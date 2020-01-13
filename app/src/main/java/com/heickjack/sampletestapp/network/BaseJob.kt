package com.heickjack.sampletestapp.network

import com.birbit.android.jobqueue.Job
import com.birbit.android.jobqueue.Params
import com.birbit.android.jobqueue.RetryConstraint
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.heickjack.sampletestapp.BuildConfig
import com.heickjack.sampletestapp.model.base.BaseResponse
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

open class BaseJob : Job(Params(1)) {
    lateinit var client: OkHttpClient
    lateinit var retrofit: Retrofit

    companion object {
        const val RETRY_COUNT = 3
        val TAG = "job_base_tag"
    }


    override fun getRetryLimit(): Int {
        return 3
    }

    override fun shouldReRunOnThrowable(throwable: Throwable, runCount: Int, maxRunCount: Int): RetryConstraint {
        return RetryConstraint.createExponentialBackoff(runCount, 1000)
    }

    override fun onAdded() {

    }

    override fun onCancel(cancelReason: Int, throwable: Throwable?) {

    }

    override fun onRun() {
        client = OkHttpClient.Builder().addNetworkInterceptor(StethoInterceptor()).build()
        retrofit = Retrofit.Builder().baseUrl(BuildConfig.ENDPOINT).addConverterFactory(GsonConverterFactory.create()).client(client).build()
    }

    fun <T> isSuccess(response: Response<T>): Boolean {
        return response.isSuccessful && response.code() == 200
    }

    fun errorBody(obj: ResponseBody?): BaseResponse {
        val errorString = obj?.string()
        if (errorString.isNullOrEmpty()) return BaseResponse() else return BaseResponse.deserialize(errorString)
    }
}