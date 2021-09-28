package com.zp.tigang.content

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.Method

object SPUtil {

    private const val SP_NAME = "TI_GANG"

    fun put(key: String, any: Any, spName: String = SP_NAME) {
        val editor = getSP(spName).edit()
        when (any) {
            is String -> editor.putString(key, any)
            is Int -> editor.putInt(key, any)
            is Boolean -> editor.putBoolean(key, any)
            is Float -> editor.putFloat(key, any)
            is Long -> editor.putLong(key, any)
            else -> editor.putString(key, any.toString())
        }
        SharedPreferencesCompat.apply(editor)
    }

    fun get(key: String, defaultObject: Any, spName: String = SP_NAME): Any = when (defaultObject) {
        is String -> getSP(spName).getString(key, defaultObject) ?: defaultObject
        is Int -> getSP(spName).getInt(key, defaultObject)
        is Boolean -> getSP(spName).getBoolean(key, defaultObject)
        is Float -> getSP(spName).getFloat(key, defaultObject)
        is Long -> getSP(spName).getLong(key, defaultObject)
        else -> "NULL"
    }

    fun remove(key: String, spName: String = SP_NAME) {
        val editor = getSP(spName).edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    fun clear(spName: String = SP_NAME) {
        val editor = getSP(spName).edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    fun contains(key: String, spName: String = SP_NAME) = getSP(spName).contains(key)


    fun getAll(spName: String = SP_NAME): Map<String, *> = getSP(spName).all

    private fun getSP(spName: String = SP_NAME) =
        appContext.getSharedPreferences(spName, Context.MODE_PRIVATE)

    private object SharedPreferencesCompat {
        private val sApplyMethod = findApplyMethod()

        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
                if (isDebug) e.printStackTrace()
            }
            return null
        }

        fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: Exception) {
                if (isDebug) e.printStackTrace()
            }
            editor.commit()
        }
    }

}