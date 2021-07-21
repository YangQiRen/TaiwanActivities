package com.yang.taiwanactivities.util

import android.util.Log

object LogCat {
    val mDebug = AppConfig.isDebug

    fun e(msg: String) {
        if (mDebug) {
            Log.e(tag(), msg)
        }
    }

    fun w(msg: String) {
        if (mDebug) {
            Log.w(tag(), msg)
        }
    }

    fun d(msg: String) {
        if (mDebug) {
            Log.d(tag(), msg)
        }
    }

    fun i(msg: String) {
        if (mDebug) {
            Log.i(tag(), msg)
        }
    }

    fun v(msg: String) {
        if (mDebug) {
            Log.v(tag(), msg)
        }
    }

    private fun tag(): String {
        return Thread.currentThread().stackTrace[4].let {
            "App# ${it.className.substringAfterLast(".")}.${it.methodName}(${it.fileName}:${it.lineNumber})"
        }
    }
}