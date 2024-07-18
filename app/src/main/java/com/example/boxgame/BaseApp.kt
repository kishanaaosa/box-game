package com.example.boxgame

import android.app.Application
import com.example.boxgame.utils.sharedpref.MyPreference
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BaseApp : Application() {
    @Inject
    lateinit var mPref: MyPreference

    companion object {
        var myPreference: MyPreference? = null
        private var instance: BaseApp? = null

        fun applicationContext(): BaseApp {
            return instance as BaseApp
        }
    }

    init {
        instance = this
    }

    override fun onCreate() {
        super.onCreate()
        myPreference = mPref
    }
}