package com.cranaya.mercadolibrechallenge.di

import com.cranaya.domain.repository.ProductSearchHistoryRepository
import com.cranaya.domain.repository.ProductRepository
import com.cranaya.domain.useCase.productDetail.GetProductDetailUseCase
import com.cranaya.domain.useCase.productList.GetProductListUseCase
import com.cranaya.domain.useCase.productSearch.GetProductSearchHistoryUseCase
import com.cranaya.domain.useCase.productSearch.IsValidSearchUseCase
import com.cranaya.domain.useCase.productSearch.SaveOrUpdateProductHistoryUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dagger module class for the domain layer
 *
 * @author Cristian Anaya
 */
@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    @Singleton
    fun provideGetProductSearchHistoryUseCase(
        productSearchHistoryRepository: ProductSearchHistoryRepository
    ): GetProductSearchHistoryUseCase = GetProductSearchHistoryUseCase(productSearchHistoryRepository)

    @Provides
    @Singleton
    fun provideIsValidSearchUseCase(
        productSearchHistoryRepository: ProductSearchHistoryRepository
    ): IsValidSearchUseCase = IsValidSearchUseCase(productSearchHistoryRepository)

    @Provides
    @Singleton
    fun provideSaveOrUpdateProductHistoryUseCase(
        productSearchHistoryRepository: ProductSearchHistoryRepository
    ): SaveOrUpdateProductHistoryUseCase = SaveOrUpdateProductHistoryUseCase(productSearchHistoryRepository)

    @Provides
    @Singleton
    fun provideGetProductListUseCase(
        productRepository: ProductRepository
    ): GetProductListUseCase = GetProductListUseCase(productRepository)

    @Provides
    @Singleton
    fun provideGetProductDetailUseCase(
        productRepository: ProductRepository
    ): GetProductDetailUseCase = GetProductDetailUseCase(productRepository)

}