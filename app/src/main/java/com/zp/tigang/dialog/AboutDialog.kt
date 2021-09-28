package com.zp.tigang.dialog

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Html
import android.view.View
import android.widget.TextView
import com.zp.tigang.R
import com.zp.tigang.common.CommonDialog
import com.zp.tigang.content.copyStr
import com.zp.tigang.content.toast

class AboutDialog : CommonDialog() {

    override fun getContentView() = R.layout.dialog_about

    override fun init(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.dialog_about_addressTxt).apply {
            text = Html.fromHtml("本应用已开源，点击访问<font color='#3379FD'>项目托管地址</font>")
            setOnClickListener {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zippo88888888/Lehua")))
            }
        }
        view.findViewById<TextView>(R.id.dialog_about_telTxt).apply {
            text = Html.fromHtml("想说点什么吗？点击获取<font color='#3379FD'>联系方式</font>")
            setOnClickListener {
                requireContext().copyStr("zp1025411146@163.com")
                "复制成功".toast()
            }
        }
    }
}