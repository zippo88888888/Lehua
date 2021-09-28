package com.zp.tigang.content

import android.content.ClipData
import android.content.ClipDescription
import android.content.ClipboardManager
import android.content.Context
import android.graphics.Point
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.zp.tigang.BuildConfig
import com.zp.tigang.common.AppManager
import com.zp.tigang.common.CommonDialog

val appManager: AppManager
    get() = AppManager.getInstance()

val isDebug: Boolean
    get() {
        return BuildConfig.IS_DEBUG
    }

val appContext: Context
    get() = appManager.getApplicationContext()

fun Context.getStatusBarHeight() = getSystemHeight("status_bar_height")

fun Context.getNBHeight() = getSystemHeight("navigation_bar_height")

private fun Context.getSystemHeight(name: String, defType: String = "dimen") =
    resources.getDimensionPixelSize(
        resources.getIdentifier(name, defType, "android")
    )

fun Context.getDdisplay() = IntArray(2).apply {
    val manager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val point = Point()
    manager.defaultDisplay.getSize(point)
    this[0] = point.x
    this[1] = point.y
}

fun Context.copyStr(content: String) {
    val cmb = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    cmb.setPrimaryClip(ClipData.newPlainText(ClipDescription.MIMETYPE_TEXT_PLAIN, content))
    "复制成功 ---> $content" log "copyStr"
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hidden() {
    visibility = View.GONE
}

fun CommonDialog.setNeedWH() {
    val display = context?.getDdisplay()
    val width =
        if (display?.isEmpty() == true) ViewGroup.LayoutParams.MATCH_PARENT else (display!![0] * 0.88f).toInt()
    dialog?.window?.setLayout(width, ViewGroup.LayoutParams.WRAP_CONTENT)
}

fun getColorById(colorId: Int) = ContextCompat.getColor(appContext, colorId)
fun getTextValue(any: Any?, isNullStr: String = "Null") = try {
    when (any) {
        is Int -> appContext.resources.getString(any)
        is String -> any
        else -> any.toString()
    }
} catch (e: Exception) {
    "getTextValue error".log()
    any?.toString() ?: isNullStr
}

fun dip2pxF(dpValue: Float) = dpValue * appContext.resources.displayMetrics.density + 0.5f
fun dip2px(dpValue: Float) = dip2pxF(dpValue).toInt()
fun px2dipF(pxValue: Float) = pxValue / appContext.resources.displayMetrics.density + 0.5f
fun px2dip(pxValue: Float) = px2dipF(pxValue).toInt()

fun Any.toast() {
    Toast.makeText(appContext, getTextValue(this), Toast.LENGTH_SHORT).show()
}

fun Any.log() {
    this log "APP_TAG"
}

infix fun Any.log(tag: Any) {
    if (isDebug) Log.i(tag.toString(), getTextValue(this))
}

fun Any.logE() {
    this logE "APP_TAG"
}

infix fun Any.logE(tag: Any) {
    if (isDebug) Log.e(tag.toString(), getTextValue(this))
}

