package com.zp.tigang.dialog

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.os.Vibrator
import android.view.View
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import com.zp.tigang.R
import com.zp.tigang.common.CommonDialog
import com.zp.tigang.content.Content
import com.zp.tigang.content.DataHelp
import com.zp.tigang.content.toast

class SetDialog : CommonDialog() {

    private var mDurationEdit: EditText? = null
    private var mCountEdit: EditText? = null
    private var mTypeRB1: RadioButton? = null
    private var mTypeRB2: RadioButton? = null
    private var mTypeRB3: RadioButton? = null

    private var mVibratorType = -1

    private var mVibrator: Vibrator? = null

    override fun getContentView() = R.layout.dialog_set

    override fun init(view: View, savedInstanceState: Bundle?) {
        mDurationEdit = view.findViewById(R.id.dialog_set_durationEdit)
        mCountEdit = view.findViewById(R.id.dialog_set_countEdit)
        mTypeRB1 = view.findViewById(R.id.dialog_set_vibratorType1)
        mTypeRB2 = view.findViewById(R.id.dialog_set_vibratorType2)
        mTypeRB3 = view.findViewById(R.id.dialog_set_vibratorType3)
        mDurationEdit?.setText("${DataHelp.getDuration()}")
        mCountEdit?.setText("${DataHelp.getCount()}")
        mVibratorType = DataHelp.getVibratorType()
        when (mVibratorType) {
            Content.VIBRATOR_TYPE1 -> mTypeRB1?.isChecked = true
            Content.VIBRATOR_TYPE2 -> mTypeRB2?.isChecked = true
            Content.VIBRATOR_TYPE3 -> mTypeRB3?.isChecked = true
        }
        view.findViewById<RadioGroup>(R.id.dialog_set_vibratorGroup).setOnCheckedChangeListener { _, checkedId ->
            mVibratorType = when (checkedId) {
                R.id.dialog_set_vibratorType1 -> {
                    mVibrator?.vibrate(Content.vibratorType1, -1)
                    Content.VIBRATOR_TYPE1
                }
                R.id.dialog_set_vibratorType2 -> {
                    mVibrator?.vibrate(Content.vibratorType2, -1)
                    Content.VIBRATOR_TYPE2
                }
                else -> {
                    mVibrator?.vibrate(Content.vibratorType3, -1)
                    Content.VIBRATOR_TYPE3
                }
            }
        }
        view.findViewById<View>(R.id.dialog_set_cancelBtn).setOnClickListener {
            dismiss()
        }
        view.findViewById<View>(R.id.dialog_set_downBtn).setOnClickListener {
            checkData()
        }
    }

    private fun checkData() {
        val duration = mDurationEdit?.text.toString()
        val count = mCountEdit?.text.toString()
        if (duration.isEmpty()) {
            "请输入间隔".toast()
            return
        }
        val durationNew = duration.toInt()
        if (durationNew < 1000) {
            "间隔最少1000毫秒".toast()
            return
        }
        if (count.isEmpty()) {
            "请输入个数".toast()
            return
        }
        val countNew = count.toInt()
        if (countNew < 5) {
            "个数最少5个".toast()
            return
        }
        if (mVibratorType == -1) {
            "请选择震动模式".toast()
            return
        }
        val c = count.toInt()
        DataHelp.apply {
            setDuration(durationNew)
            setCount(countNew)
            setVibratorType(mVibratorType)
        }
        dismiss()
    }

    override fun onStart() {
        super.onStart()
        mVibrator = (context as? Activity)?.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        mVibrator?.cancel()
    }

}