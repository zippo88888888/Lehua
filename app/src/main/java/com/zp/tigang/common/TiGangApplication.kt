package com.zp.tigang.common

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import com.zp.tigang.content.appManager

class TiGangApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appManager.init(this)
    }

    override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

}