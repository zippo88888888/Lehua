package com.zp.tigang.common

import android.app.Dialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.zp.tigang.R
import com.zp.tigang.content.setNeedWH

abstract class CommonDialog : DialogFragment() {

    var dialogClick: ((view: View, value: Any?) -> Unit)? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutID = getContentView()
        if (layoutID <= 0) throw NullPointerException("DialogFragment ContentView is not null")
        return inflater.inflate(getContentView(), container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?) = createDialog(savedInstanceState)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        init(view, savedInstanceState)
        dialog?.setOnKeyListener { _, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_BACK && event.action == KeyEvent.ACTION_DOWN)
                onBackPressed()
            else false
        }
    }

    abstract fun getContentView(): Int
    abstract fun init(view: View, savedInstanceState: Bundle?)

    protected open fun createDialog(savedInstanceState: Bundle?) = Dialog(requireContext(), R.style.Fillet_Dialog).apply {
        window?.setGravity(Gravity.CENTER)
    }

    override fun onStart() {
        super.onStart()
        setNeedWH()
    }

    /**
     * 返回true 拦截，否则销毁
     */
    open fun onBackPressed() = false

}