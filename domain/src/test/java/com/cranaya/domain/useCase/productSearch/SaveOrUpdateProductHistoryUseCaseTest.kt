package com.cranaya.domain.useCase.productSearch

import com.cranaya.domain.factory.ProductSearchHistoryFactory.generateProductSearchHistory
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [IsValidSearchUseCase]
 *
 * @author Cristian Anaya
 */
class SaveOrUpdateProductHistoryUseCaseTest {
    private lateinit var saveOrUpdateProductHistoryUseCase: SaveOrUpdateProductHistoryUseCase

    @Mock
    lateinit var searchRepository: ProductSearchHistoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        saveOrUpdateProductHistoryUseCase = SaveOrUpdateProductHistoryUseCase(searchRepository)
    }

    @Test
    fun `test when insert product history then return result success`() = runBlocking {
        val productHistoryModel = generateProductSearchHistory()

        Mockito.`when`(searchRepository.insertOrUpdateSearchHistory(productHistoryModel))
            .thenReturn(1)

        val result = saveOrUpdateProductHistoryUseCase.execute(productHistoryModel)

        assert(result == 1)
    }

    @Test
    fun `test when insert product history then return result failed`() = runBlocking {
        val productHistoryModel = generateProductSearchHistory()

        Mockito.`when`(searchRepository.insertOrUpdateSearchHistory(productHistoryModel))
            .thenReturn(0)

        val result = saveOrUpdateProductHistoryUseCase.execute(productHistoryModel)

        assert(result == 0)
    }
}