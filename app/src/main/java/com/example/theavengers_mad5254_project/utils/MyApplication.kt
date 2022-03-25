package com.example.theavengers_mad5254_project.utils

import android.content.Context
import android.support.multidex.MultiDex
import android.support.multidex.MultiDexApplication

class MyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppPreference.init(this)
    }

     override fun attachBaseContext(context: Context?) {
        super.attachBaseContext(context)
        MultiDex.install(this)
    }
companion object {
    private var mInstance: MyApplication? = null
    fun getInstance(): MyApplication? {
        return mInstance
    }
}

}