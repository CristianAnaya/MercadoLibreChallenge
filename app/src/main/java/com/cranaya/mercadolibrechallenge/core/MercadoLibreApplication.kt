package com.cranaya.mercadolibrechallenge.core

import android.app.Application
import android.util.Log
import com.cranaya.domain.utils.interfaces.IErrorLogger
import dagger.hilt.android.HiltAndroidApp
import kotlin.system.exitProcess

/**
 * [Application] implementation for custom initialization and configuration
 *
 * @author Cristian Anaya
 */
@HiltAndroidApp
class MercadoLibreApplication : Application(), IErrorLogger {

    override fun onCreate() {
        super.onCreate()
        logAllErrors()
    }

    /**
     * Log of all exceptions that may occur during the life of the application
     * including those that can make it crash
     */
    private fun logAllErrors() {
        Thread.setDefaultUncaughtExceptionHandler { _, paramThrowable ->
            logError(javaClass.simpleName, paramThrowable.localizedMessage ?: "")
            exitProcess(2)
        }
    }

    override fun logError(tag: String, errorMessage: String?) {
        Log.e(tag, errorMessage ?: "")
    }
}