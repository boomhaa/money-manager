package com.example.money_manager

import android.app.Application
import com.google.crypto.tink.config.TinkConfig
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoneyManagerApp : Application(){
    override fun onCreate() {
        super.onCreate()
        TinkConfig.register()
    }
}