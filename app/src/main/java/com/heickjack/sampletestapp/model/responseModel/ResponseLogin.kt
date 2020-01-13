package com.heickjack.sampletestapp.model.responseModel

import com.google.gson.Gson
import com.heickjack.sampletestapp.model.base.BaseResponse

data class ResponseLogin(
    var id: String,
    var token: String
) : BaseResponse() {

    fun serialize(): String {
        return Gson().toJson(this)
    }

    companion object {
        fun deserialize(json: String): ResponseLogin {
            return Gson().fromJson(json, ResponseLogin::class.java)
        }
    }
}