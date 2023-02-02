package com.cranaya.data.helpers

import android.util.Log
import com.cranaya.domain.utils.interfaces.IErrorLogger

class ErrorLoggerImpl : IErrorLogger {

    override fun logError(tag: String, errorMessage: String?) {
        Log.e(tag, errorMessage!!)
    }
}