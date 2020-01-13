package com.heickjack.sampletestapp.model


class Resource<T, V> private constructor(var status: Status? = null,
                                         var data: T? = null,
                                         var error: V? = null,
                                         var throwable: Throwable? = null,
                                         var message: String? = null) {
    enum class Status {
        SUCCESS, ERROR, THROWABLE
    }

    companion object {

        val TAG = "RESOURCE"

        fun <T, V> success(data: T?): Resource<T, V> {
            return Resource(Status.SUCCESS, data)
        }

        fun <T, V> error(msg: String?, error: V?): Resource<T, V> {
            return Resource(Status.ERROR, null, error, null, msg)
        }

        fun <T, V> throwable(throwable: Throwable?): Resource<T, V> {
            return Resource(Status.THROWABLE, null, null, throwable)
        }
    }
}
