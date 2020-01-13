package com.heickjack.sampletestapp.model.postmodel

import com.google.gson.Gson

data class PostLoginModel(val email: String, val password: String) {

    fun serialize():String{
        return Gson().toJson(this)
    }

}