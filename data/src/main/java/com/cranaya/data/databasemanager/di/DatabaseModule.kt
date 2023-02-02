package com.cranaya.data.databasemanager.di

import android.content.Context
import com.cranaya.data.databasemanager.MercadoLibreDatabase
import com.cranaya.data.databasemanager.dao.ProductSearchHistoryDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module class for the Database
 *
 * @author Cristian Anaya
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): MercadoLibreDatabase =
        MercadoLibreDatabase.getInstance(context)

    @Provides
    fun provideSearchHistoryDao(mercadoLibreDatabase: MercadoLibreDatabase): ProductSearchHistoryDao = mercadoLibreDatabase.searchHistoryDao()

}