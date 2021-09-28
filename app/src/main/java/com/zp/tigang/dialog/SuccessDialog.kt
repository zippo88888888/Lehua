package com.zp.tigang.dialog

import android.os.Bundle
import android.view.View
import com.zp.tigang.R
import com.zp.tigang.common.CommonDialog

class SuccessDialog : CommonDialog() {

    override fun getContentView() = R.layout.dialog_success

    override fun init(view: View, savedInstanceState: Bundle?) {
        view.findViewById<View>(R.id.dialog_success_downBtn).setOnClickListener {
            dialogClick?.invoke(it, null)
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        dialog?.setCancelable(false)
    }
}