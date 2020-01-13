package com.heickjack.sampletestapp

import android.app.Application
import com.birbit.android.jobqueue.JobManager
import com.birbit.android.jobqueue.log.CustomLogger
import com.facebook.stetho.Stetho

class MyApplication : Application() {

    private lateinit var jobManager: JobManager

    companion object {
        lateinit var instance: MyApplication
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initJob()
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }

    private fun initJob() {
        val configuration = com.birbit.android.jobqueue.config.Configuration.Builder(this)
            .customLogger(object : CustomLogger {

                override fun v(text: String, vararg args: Any) {
                }

                override fun isDebugEnabled(): Boolean {
                    return BuildConfig.DEBUG
                }

                override fun d(text: String, vararg args: Any) {}

                override fun e(t: Throwable, text: String, vararg args: Any) {}

                override fun e(text: String, vararg args: Any) {}
            })
            .minConsumerCount(1)
            .maxConsumerCount(3)
            .loadFactor(3)
            .consumerKeepAlive(120)
            .build()

        jobManager = com.birbit.android.jobqueue.JobManager(configuration)
    }

    fun getJobManager(): JobManager {
        return jobManager
    }
}