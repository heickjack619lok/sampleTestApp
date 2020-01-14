package com.heickjack.sampletestapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.datafm.BaseActivity
import com.heickjack.sampletestapp.R
import com.heickjack.sampletestapp.databinding.ActivityHomeBinding
import com.heickjack.sampletestapp.model.postmodel.PostGetListModel
import com.heickjack.sampletestapp.model.postmodel.PostUpdateModel
import com.heickjack.sampletestapp.util.PreferenceUtil
import com.heickjack.sampletestapp.viewmodel.HomeViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Section

class HomeActivity : BaseActivity(), SwipeRefreshLayout.OnRefreshListener {

    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var mViewModel: HomeViewModel

    private var mAdapter: GroupAdapter<GroupieViewHolder>? = null
    private var mSection: Section? = null
    private var mLayoutManager: LinearLayoutManager? = null
    private val TAKE = 15
    private var isLoading = true
    private var hasMore = true
    private val VISIBLE_THRESHOLD = 5

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        super.loading = mBinding.loadingLayout

        prepareRecyclerView()
        prepareDataListener()
        getData(0)
        mBinding.swipeRefreshLayout.setOnRefreshListener(this)
        mBinding.toolbar.setNavigationOnClickListener {
            PreferenceUtil.logout(this)
            finishAffinity()
            LoginActivity.start(this)
        }
    }

    private fun getData(skip: Int) {
        showLoading()
        mViewModel.getList(
            this,
            PostGetListModel(PreferenceUtil.getUser(this)!!.id, PreferenceUtil.getAuthToken(this))
        )
    }

    private fun prepareRecyclerView() {
        mSection = Section()

        mAdapter = GroupAdapter()
        mAdapter?.add(mSection!!)

        mLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mBinding.recyclerView.adapter = mAdapter
        mBinding.recyclerView.layoutManager = mLayoutManager
        hookLoadMoreListener()

        mAdapter?.setOnItemLongClickListener { item, view ->
            if (item is ListItemMerchant) {
                val builder = AlertDialog.Builder(view.context)
                val mUpdateView = layoutInflater.inflate(R.layout.view_update, null)
                mUpdateView.findViewById<EditText>(R.id.input_name).setText(item.merchant.list_name)
                mUpdateView.findViewById<EditText>(R.id.input_distance)
                    .setText(item.merchant.distance)

                builder
                    .setView(mUpdateView)
                    .setPositiveButton(
                        R.string.text_update
                    ) { _, _ ->
                        showLoading()
                        mViewModel.updateListItem(
                            this@HomeActivity,
                            PostUpdateModel(
                                PreferenceUtil.getUser(view.context)!!.id,
                                PreferenceUtil.getAuthToken(view.context),
                                item.merchant.id,
                                mUpdateView.findViewById<EditText>(R.id.input_name).text.toString(),
                                mUpdateView.findViewById<EditText>(R.id.input_distance).text.toString()
                            )
                        )
                    }.setNegativeButton(R.string.text_cancel, null)
                    .create()
                    .show()
            }
            true
        }
        mAdapter?.setOnItemClickListener { item, view ->
            if (item is ListItemMerchant){
                Toast.makeText(
                    view.context,
                    item.merchant.list_name + "(" + item.merchant.distance + "km away)",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun hookLoadMoreListener() {
        mBinding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                if (mLayoutManager != null) {
                    val totalItemCount = mSection!!.itemCount
                    val lastVisibleItem = mLayoutManager!!.findLastCompletelyVisibleItemPosition()

                    val isCloseToTheEnd = totalItemCount < lastVisibleItem + VISIBLE_THRESHOLD

                    if (!isLoading && isCloseToTheEnd && hasMore) {
                        loadMore(totalItemCount)
                    }
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (mLayoutManager != null) {
                    val totalItemCount = mSection!!.itemCount
                    val lastVisibleItem = mLayoutManager!!.findLastCompletelyVisibleItemPosition()

                    val isCloseToTheEnd = totalItemCount < lastVisibleItem + VISIBLE_THRESHOLD

                    if (!isLoading && isCloseToTheEnd && hasMore) {
                        loadMore(totalItemCount)
                    }
                }
            }
        })
    }

    private fun loadMore(totalItemCount: Int) {
        isLoading = true
        getData(totalItemCount)
    }

    private fun prepareDataListener() {
        mViewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        mViewModel.onGetListListener().observe(this, Observer {
            hideLoading()
            mBinding.swipeRefreshLayout.isRefreshing = false
            mSection?.clear()
            for (item in it) {
                mSection?.add(ListItemMerchant(item))
            }
            mAdapter?.notifyDataSetChanged()
            isLoading = false
            hasMore = false
        })
        mViewModel.onUpdateListener().observe(this, Observer {
            //refresh data, since updated value is not returned from api
            getData(0)
        })

        mViewModel.onError().observe(this, Observer {
            hideLoading()
            if (!it.status?.message.isNullOrEmpty()) {
                Toast.makeText(this, it.status?.message, Toast.LENGTH_SHORT).show()
            }
        })
        mViewModel.onThrowable().observe(this, Observer {
            hideLoading()
            if (!it.message.isNullOrEmpty()) {
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onRefresh() {
        getData(0)
    }
}