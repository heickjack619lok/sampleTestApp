package com.heickjack.sampletestapp.model.postmodel

import com.google.gson.Gson

data class PostGetListModel(val id:String, val token:String) {

    fun serialize():String{
        return Gson().toJson(this)
    }
}