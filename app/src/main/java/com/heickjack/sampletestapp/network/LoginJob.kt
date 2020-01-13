package com.heickjack.sampletestapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heickjack.sampletestapp.model.Resource
import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.postmodel.PostLoginModel
import com.heickjack.sampletestapp.model.responseModel.User
import com.heickjack.sampletestapp.util.PreferenceUtil

class LoginJob(private val postModel: PostLoginModel) : BaseJob() {

    private var data = MutableLiveData<Resource<User, BaseResponse>>()

    override fun onRun() {
        super.onRun()

            val responseLogin = retrofit.create(HttpClient::class.java).login(postModel).execute()

            if (isSuccess(responseLogin) && responseLogin.body() != null) {
                if (responseLogin.body()!!.status!!.code == 200){
                    val user = User(responseLogin.body()!!.id, responseLogin.body()!!.token)
                    PreferenceUtil.login(applicationContext, user)
                    data.postValue(Resource.success(user))
                }else{
                    data.postValue(Resource.error(null, responseLogin.body()))
                }
            } else {
                data.postValue(Resource.error(null, errorBody(responseLogin.errorBody())))
            }
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        super.onCancel(cancelReason, throwable)
        data.postValue(Resource.throwable(throwable))
    }

    fun onResult(): LiveData<Resource<User, BaseResponse>> {
        return data
    }
}