package com.cranaya.domain.useCase.productSearch

import com.cranaya.domain.factory.ProductSearchHistoryFactory.generateProductSearchHistoryFactoryList
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import com.cranaya.domain.useCase.productList.GetProductListUseCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [GetProductSearchHistoryUseCase]
 *
 * @author Cristian Anaya
 */
class GetProductSearchHistoryUseCaseTest {
    private lateinit var getProductHistoryUseCase: GetProductSearchHistoryUseCase

    @Mock
    lateinit var searchRepository: ProductSearchHistoryRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductHistoryUseCase = GetProductSearchHistoryUseCase(searchRepository)
    }

    @Test
    fun `get product history list saved in the local database`() = runBlocking {
        val notEmptyListResponse = generateProductSearchHistoryFactoryList(5)
        //GIVEN
        val inputFlow = flowOf(Resource.Success(notEmptyListResponse))
        `when`(searchRepository.getSearchHistory()).thenReturn(inputFlow)
        //WHEN
        val output = getProductHistoryUseCase.execute().toList()
        //THEN
        assert(output[0].data?.size == 5)
    }

    @Test
    fun `get empty product history list saved in the local database`() = runBlocking {
        val emptyListResponse = generateProductSearchHistoryFactoryList(0)
        //GIVEN
        val inputFlow = flowOf(Resource.Success(emptyListResponse))
        `when`(searchRepository.getSearchHistory()).thenReturn(inputFlow)
        //WHEN
        val output = getProductHistoryUseCase.execute().toList()
        //THEN
        assert(output[0].data?.size == 0)
    }
}