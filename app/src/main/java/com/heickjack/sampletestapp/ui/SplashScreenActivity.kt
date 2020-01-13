package com.heickjack.sampletestapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.heickjack.sampletestapp.R
import com.heickjack.sampletestapp.util.PreferenceUtil

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.button_part_b).setOnClickListener {
            if (PreferenceUtil.isLogin(this)){
                HomeActivity.start(this)
            }else{
            LoginActivity.start(this)
            }
        }
        findViewById<Button>(R.id.button_part_c).setOnClickListener {
           PrimeNumberActivity.start(this)
        }
    }
}
