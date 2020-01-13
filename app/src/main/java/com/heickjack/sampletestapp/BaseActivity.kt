package com.android.datafm

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.heickjack.sampletestapp.model.base.BaseResponse
import com.heickjack.sampletestapp.ui.LoginActivity
import com.heickjack.sampletestapp.util.PreferenceUtil

abstract class BaseActivity : AppCompatActivity() {


    protected var loading: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loading?.setOnClickListener { }
    }

    fun showLoading() {
        loading?.setOnClickListener { }
        loading?.visibility = View.VISIBLE
    }

    fun hideLoading() {
        loading?.setOnClickListener { }
        loading?.visibility = View.GONE
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun logout() {
        hideLoading()
        PreferenceUtil.logout(this)
        finishAffinity()
        LoginActivity.start(this)
    }

    open fun onErrorListener(error: BaseResponse?) {
        hideLoading()
        if (!error?.status?.message.isNullOrEmpty()) {
            Toast.makeText(this, error!!.status!!.message, Toast.LENGTH_SHORT).show()
        }
    }

    open fun onThrowableListener(throwable: Throwable?) {
        hideLoading()
        if (!throwable?.message.isNullOrEmpty()) {
            Toast.makeText(this, throwable?.message, Toast.LENGTH_SHORT).show()
        }
    }
}