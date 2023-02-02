package com.cranaya.data.di

import com.cranaya.data.databasemanager.dao.ProductSearchHistoryDao
import com.cranaya.data.repository.ProductSearchHistoryRepositoryImpl
import com.cranaya.data.repository.ProductRepositoryImpl
import com.cranaya.data.requestmanager.mapper.ProductMapper
import com.cranaya.data.requestmanager.service.ProductService
import com.cranaya.domain.utils.interfaces.IErrorLogger
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import com.cranaya.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module class for the data layer
 *
 * @author Cristian Anaya
 */
@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideProductSearchRepository(
        productSearchHistoryDao: ProductSearchHistoryDao,
        iErrorLogger: IErrorLogger
    ): ProductSearchHistoryRepository {
        return ProductSearchHistoryRepositoryImpl(
            productSearchHistoryDao,
            iErrorLogger
        )
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        productService: ProductService,
        productMapper: ProductMapper,
        iErrorLogger: IErrorLogger
    ): ProductRepository {
        return ProductRepositoryImpl(
            productService,
            productMapper,
            iErrorLogger
        )
    }
}