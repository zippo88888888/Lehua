package com.zp.tigang

import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.zp.tigang.common.CommonActivity
import com.zp.tigang.common.TimeLiveData
import com.zp.tigang.content.DataHelp
import com.zp.tigang.content.toast
import com.zp.tigang.dialog.AboutDialog
import com.zp.tigang.dialog.SetDialog
import com.zp.tigang.dialog.SuccessDialog

class MainActivity : CommonActivity() {

    private var mJuhua: JuHua? = null
    private var mCountTxt: TextView? = null
    private var mStateTxt: TextView? = null
    private var mStartBtn: TextView? = null

    private var mIsStart = false
    private val mTimer by lazy {
        TimeLiveData.get(3000)
    }

    private var mCount = 0

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_main_set -> {
                showSetDialog()
            }
            R.id.menu_main_study -> {
                "教程".toast()
            }
            R.id.menu_main_theme -> {
                "主题".toast()
            }
            R.id.menu_main_about -> {
                showAboutDialog()
            }
            R.id.menu_main_speak -> {
                showTipDialog()
            }
        }
        return super.onMenuItemClick(item)
    }

    override fun getContentView() = R.layout.activity_main

    override fun initAll() {
        toolBarTitle = R.string.app_name
        noIcon()
        mCountTxt = findViewById(R.id.main_txt1)
        mStateTxt = findViewById(R.id.main_txt2)
        mJuhua = findViewById(R.id.main_juhua)
        mStartBtn = findViewById(R.id.main_startBtn)
        mStartBtn?.setOnClickListener {
            if (mIsStart) {
                setEndState()
            } else {
                setStartState()
            }
            mToolbar?.menu?.findItem(R.id.menu_main_set)?.isVisible = mIsStart
            mIsStart = !mIsStart
        }
        mTimer.observe(this) {
            mJuhua?.startAnim()
            if (mJuhua?.animState() == true) {
                mStateTxt?.setText(R.string.jiajin)
            } else {
                mStateTxt?.setText(R.string.fangsong)
                ++ mCount
                mCountTxt?.text = "$mCount"
                if (mCount >= DataHelp.getCount()) {
                    mTimer.cancel()
                    mJuhua?.cancelAll()
                    showSuccessDialog()
                }
            }
        }
    }

    private fun setStartState() {
        mStartBtn?.setText(R.string.end)
        mTimer.start()
        mJuhua?.startAnim()
        mStateTxt?.setText(R.string.jiajin)
    }

    private fun setEndState() {
        mStartBtn?.setText(R.string.start)
        mTimer.cancel()
        mJuhua?.cancelAll()
        mCount = 0
        mCountTxt?.text = "$mCount"
        mStateTxt?.setText(R.string.fangsong)
    }

    private fun showSetDialog() {
        SetDialog().show(supportFragmentManager, "SetDialog")
    }

    private fun showAboutDialog() {
        AboutDialog().show(supportFragmentManager, "AboutDialog")
    }

    private fun showTipDialog() {
        AlertDialog.Builder(this).apply {
            setTitle(R.string.speak)
            setMessage(R.string.tip_info)
            setPositiveButton(R.string.i_know) { dialog, _ ->
                dialog.dismiss()
            }
            show()
        }
    }

    private fun showSuccessDialog() {
        DataHelp.setSuccessCount(1)
        SuccessDialog().apply {
            dialogClick = { _,_ ->
                setEndState()
                mIsStart = !mIsStart
                mToolbar?.menu?.findItem(R.id.menu_main_set)?.isVisible = true
            }
            show(supportFragmentManager, "SuccessDialog")
        }
    }

    override fun back() {
        "已完成${DataHelp.getSuccessCount()}次运动".toast()
    }
}