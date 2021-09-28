package com.zp.tigang.common

import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.zp.tigang.R
import com.zp.tigang.content.getStatusBarHeight
import com.zp.tigang.content.getTextValue

abstract class CommonActivity : AppCompatActivity(), View.OnClickListener,
    Toolbar.OnMenuItemClickListener {

    protected var mToolbar: Toolbar? = null
    protected var toolBarTitle: Any = ""
        set(value) {
            supportActionBar?.title = ""
            mToolbar?.title = getTextValue(value)
            field = value
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(getContentView())
        initBar()
        initAll()
    }

    abstract fun getContentView(): Int

    abstract fun initAll()

    override fun onClick(v: View?) = Unit

    override fun onMenuItemClick(item: MenuItem?) = true

    final override fun getResources(): Resources {
        val res = super.getResources()
        val config = Configuration()
        config.setToDefaults()
        res.updateConfiguration(config, res.displayMetrics)
        return res
    }

    protected open fun getStatusBarLP() = LinearLayout.LayoutParams(
        ViewGroup.LayoutParams.MATCH_PARENT, getStatusBarHeight()
    )

    open fun back() {
        onBackPressed()
    }

    protected fun noIcon() {
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
        mToolbar?.setNavigationIcon(R.drawable.logo_svg)
    }

    private fun initBar() {
        mToolbar = findViewById(R.id.tool_bar)
        setSupportActionBar(mToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        mToolbar?.setNavigationOnClickListener { back() }
        mToolbar?.setOnMenuItemClickListener(this)
        findViewById<View>(R.id.tool_bar_statusView).layoutParams = getStatusBarLP()
    }

}