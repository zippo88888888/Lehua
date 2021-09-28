package com.zp.tigang.common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class AppManager {

    private var mApplicationCon: Context? = null

    private object Builder {
        @SuppressLint("StaticFieldLeak") val MANAGER = AppManager()
    }

    companion object {
        fun getInstance() = Builder.MANAGER
    }

    fun init(applicationCon: Application) {
        this.mApplicationCon = applicationCon
    }

    fun getApplicationContext() =
        if (mApplicationCon == null) throw NullPointerException("请先调用\"init()\"方法")
        else mApplicationCon!!

}