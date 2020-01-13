package com.heickjack.sampletestapp.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.heickjack.sampletestapp.MyApplication
import com.heickjack.sampletestapp.model.Resource
import com.heickjack.sampletestapp.model.postmodel.PostLoginModel
import com.heickjack.sampletestapp.model.responseModel.User
import com.heickjack.sampletestapp.network.LoginJob

class LoginViewModel : BaseViewModel() {

    private val userLiveData = MutableLiveData<User>()

    fun login(lifecycleOwner: LifecycleOwner, postModel:PostLoginModel){
        val job = LoginJob(postModel)
        job.onResult().observe(lifecycleOwner, Observer {
            if (it?.status != null){
                when(it.status){
                    Resource.Status.SUCCESS -> {
                        userLiveData.postValue(it.data)
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

    fun onLoginListener():MutableLiveData<User>{
        return userLiveData
    }
}