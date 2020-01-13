package com.heickjack.sampletestapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.datafm.BaseActivity
import com.heickjack.sampletestapp.R
import com.heickjack.sampletestapp.databinding.ActivityLoginBinding
import com.heickjack.sampletestapp.model.postmodel.PostLoginModel
import com.heickjack.sampletestapp.util.PreferenceUtil
import com.heickjack.sampletestapp.viewmodel.LoginViewModel

class LoginActivity : BaseActivity(), View.OnClickListener {

    private lateinit var mBinding: ActivityLoginBinding
    private lateinit var model: LoginViewModel

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        prepareDataListener()

        super.loading = mBinding.loadingLayout
        mBinding.buttonLogin.setOnClickListener(this)
    }

    private fun prepareDataListener() {
        model = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        model.onLoginListener().observe(this, Observer {
            hideLoading()
            if (PreferenceUtil.isLogin(this)){
                HomeActivity.start(this)
            }
        })
        model.onError().observe(this, Observer {
            hideLoading()
            if(!it.status?.message.isNullOrEmpty()){
                Toast.makeText(this, it.status?.message, Toast.LENGTH_SHORT).show()
            }
        })
        model.onThrowable().observe(this, Observer {
            hideLoading()
            if(!it.message.isNullOrEmpty()){
                Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onClick(v: View?) {
        when (v) {
            mBinding.buttonLogin -> proceedLogin()
        }
    }

    private fun proceedLogin() {
        if (mBinding.inputEmail.text.toString().isNotEmpty() && mBinding.inputPassword.text.toString().isNotEmpty()) {
            showLoading()
            model.login(this, PostLoginModel(mBinding.inputEmail.text.toString(), mBinding.inputPassword.text.toString()))
        } else {
            if (mBinding.inputEmail.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.warning_email_empty), Toast.LENGTH_SHORT).show()
            } else if (mBinding.inputPassword.text.toString().isEmpty()) {
                Toast.makeText(this, getString(R.string.warning_password_empty), Toast.LENGTH_SHORT).show()
            }
        }
    }
}