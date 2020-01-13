package com.heickjack.sampletestapp.network

import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.postmodel.PostGetListModel
import com.heickjack.sampletestapp.model.postmodel.PostLoginModel
import com.heickjack.sampletestapp.model.postmodel.PostUpdateModel
import com.heickjack.sampletestapp.model.responseModel.ResponseGetList
import com.heickjack.sampletestapp.model.responseModel.ResponseLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface HttpClient {

    @POST("login")
    fun login(@Body postModel: PostLoginModel):Call<ResponseLogin>

    @POST("listing")
    fun getList(@Body postModel: PostGetListModel):Call<ResponseGetList>

    @POST("listing/update")
    fun updateItem(@Body postModel:PostUpdateModel):Call<BaseResponse>
}