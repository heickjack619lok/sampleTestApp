package com.heickjack.sampletestapp.model.responseModel

import com.google.gson.Gson
import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.model.Merchant

data class ResponseGetList(val listing: ArrayList<Merchant>?) : BaseResponse() {

    companion object {
        fun deserialize(json: String): ResponseGetList {
            return Gson().fromJson(json, ResponseGetList::class.java)
        }
    }
}