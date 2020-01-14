package com.heickjack.sampletestapp.network

import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.postmodel.PostGetListModel
import com.heickjack.sampletestapp.model.postmodel.PostLoginModel
import com.heickjack.sampletestapp.model.postmodel.PostUpdateModel
import com.heickjack.sampletestapp.model.responseModel.ResponseGetList
import com.heickjack.sampletestapp.model.responseModel.ResponseLogin
import retrofit2.Call
import retrofit2.http.*

interface HttpClient {

    @POST("login")
    @FormUrlEncoded
    fun login(@Field("email") email:String,
              @Field("password") password:String):Call<ResponseLogin>

    @GET("listing")
    fun getList(@Query("id") id:String,
                @Query("token") token:String):Call<ResponseGetList>

    @POST("listing/update")
    @FormUrlEncoded
    fun updateItem(@Field("id") id:String,
                   @Field("token") token: String,
                   @Field("listing_id") listingId:String,
                   @Field("listing_name") listingName:String,
                   @Field("distance") distance:String):Call<BaseResponse>
}