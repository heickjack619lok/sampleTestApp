package com.heickjack.sampletestapp.util

import android.content.Context
import android.preference.PreferenceManager
import com.heickjack.sampletestapp.model.responseModel.User

class PreferenceUtil {

    companion object {
        private const val USER = "USER"
        private const val AUTH_TOKEN = "AUTH_TOKEN"

        fun putUser(context: Context, user: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(USER, user).apply()
        }

        fun getUser(context: Context): User? {
            return if (PreferenceManager.getDefaultSharedPreferences(context).getString(USER, "").isNullOrEmpty()
            ) {
                null
            } else {
                User.deserialize(PreferenceManager.getDefaultSharedPreferences(context).getString(USER, ""))
            }
        }

        fun putAuthToken(context: Context, token: String?) {
            PreferenceManager.getDefaultSharedPreferences(context).edit().putString(AUTH_TOKEN, token).apply()
        }

        fun getAuthToken(context: Context): String {
            return if (!PreferenceManager.getDefaultSharedPreferences(context).getString(
                    AUTH_TOKEN,
                    ""
                ).isNullOrEmpty()
            ) {
                PreferenceManager.getDefaultSharedPreferences(context).getString(AUTH_TOKEN, "")!!
            } else {
                ""
            }
        }

        fun login(context: Context, user: User) {
            putUser(context, user.serialize())
            putAuthToken(context, user.token)
        }

        fun logout(context: Context) {
            putUser(context, "")
            putAuthToken(context, "")
        }

        fun isLogin(context: Context): Boolean {
            return getUser(context) != null && getAuthToken(context).isNotEmpty()
        }

        fun updateUser(context: Context, user: User){
            user.token = getAuthToken(context)
            putUser(context, user.serialize())
        }
    }
}