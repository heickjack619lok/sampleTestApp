package com.heickjack.sampletestapp.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.heickjack.sampletestapp.model.Resource
import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.postmodel.PostUpdateModel

class UpdateListItemJob(private val postModel: PostUpdateModel) : BaseJob() {

    private var data = MutableLiveData<Resource<BaseResponse, BaseResponse>>()

    override fun onRun() {
        super.onRun()

        val response = retrofit
            .create(HttpClient::class.java)
            .updateItem(
                postModel.id,
                postModel.token,
                postModel.listing_id,
                postModel.listing_name,
                postModel.distance
            )
            .execute()

        if (isSuccess(response) && response.body() != null) {
            if (response.body()!!.status!!.code == 200) {
                data.postValue(Resource.success(response.body()))
            } else {
                data.postValue(Resource.error(null, response.body()))
            }
        } else {
            data.postValue(Resource.error(null, errorBody(response.errorBody())))
        }
    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {
        super.onCancel(cancelReason, throwable)
        data.postValue(Resource.throwable(throwable))
    }

    fun onResult(): LiveData<Resource<BaseResponse, BaseResponse>> {
        return data
    }
}