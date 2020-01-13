package com.heickjack.sampletestapp.model.base

import com.google.gson.Gson
import com.heickjack.sampletestapp.model.Status

open class BaseResponse(var status: Status? = null) {

    companion object{
        fun deserialize(json:String):BaseResponse{
            return Gson().fromJson(json, BaseResponse::class.java)
        }
    }
}