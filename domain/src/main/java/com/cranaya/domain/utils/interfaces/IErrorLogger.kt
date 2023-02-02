package com.cranaya.domain.utils.interfaces

interface IErrorLogger {
    fun logError(tag: String, errorMessage: String?)
}