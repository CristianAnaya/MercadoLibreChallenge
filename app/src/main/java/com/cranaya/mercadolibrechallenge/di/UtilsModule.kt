package com.cranaya.mercadolibrechallenge.di

import com.cranaya.data.helpers.ErrorLoggerImpl
import com.cranaya.domain.utils.interfaces.IErrorLogger
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UtilsModule {

    @Provides
    @Singleton
    fun provideErrorLogger(): IErrorLogger {
        return ErrorLoggerImpl()
    }
}