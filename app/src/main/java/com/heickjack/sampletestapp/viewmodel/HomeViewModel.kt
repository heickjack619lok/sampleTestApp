package com.heickjack.sampletestapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.heickjack.sampletestapp.MyApplication
import com.heickjack.sampletestapp.model.Merchant
import com.heickjack.sampletestapp.model.Resource
import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.postmodel.PostGetListModel
import com.heickjack.sampletestapp.model.postmodel.PostUpdateModel
import com.heickjack.sampletestapp.network.GetListJob
import com.heickjack.sampletestapp.network.UpdateListItemJob

class HomeViewModel : BaseViewModel() {

    private val listLiveData = MutableLiveData<ArrayList<Merchant>>()
    private val updateLiveData = MutableLiveData<BaseResponse>()

    fun getList(lifecycleOwner: LifecycleOwner, postModel:PostGetListModel){
        val job = GetListJob(postModel)
        job.onResult().observe(lifecycleOwner, Observer {
            if (it?.status != null){
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        listLiveData.postValue(it.data)
                    }
                    Resource.Status.ERROR -> {
                        serverErrorMutableLiveData.postValue(it.error)
                    }
                    Resource.Status.THROWABLE -> {
                        throwableMutableLiveData.postValue(it.throwable)
                    }
                }
            }
        })
        MyApplication.instance.getJobManager().addJobInBackground(job)
    }

    fun updateListItem(lifecycleOwner: LifecycleOwner, postModel:PostUpdateModel){
        val job = UpdateListItemJob(postModel)
        job.onResult().observe(lifecycleOwner, Observer {
            if (it?.status != null){
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        updateLiveData.postValue(it.data)
                    }
                    Resource.Status.ERROR -> {
                        serverErrorMutableLiveData.postValue(it.error)
                    }
                    Resource.Status.THROWABLE -> {
                        throwableMutableLiveData.postValue(it.throwable)
                    }
                }
            }
        })
        MyApplication.instance.getJobManager().addJobInBackground(job)
    }

    fun onGetListListener():MutableLiveData<ArrayList<Merchant>>{
        return listLiveData
    }

    fun onUpdateListener():MutableLiveData<BaseResponse>{
        return updateLiveData
    }
}