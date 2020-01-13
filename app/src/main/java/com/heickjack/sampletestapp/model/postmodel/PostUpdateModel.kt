package com.heickjack.sampletestapp.model.postmodel

import com.google.gson.Gson

data class PostUpdateModel(var id:String,
                           var token:String,
                           var listing_id:String,
                           var listing_name:String,
                           var distance:String) {

    fun serialize():String{
        return Gson().toJson(this)
    }
}