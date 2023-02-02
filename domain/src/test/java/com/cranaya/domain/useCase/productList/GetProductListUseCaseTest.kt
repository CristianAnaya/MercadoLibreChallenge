package com.cranaya.domain.useCase.productList

import com.cranaya.domain.factory.ProductFactory
import com.cranaya.domain.factory.ProductSearchHistoryFactory
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductRepository
import com.cranaya.domain.useCase.productDetail.GetProductDetailUseCase
import junit.framework.TestCase
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [GetProductListUseCase]
 *
 * @author Cristian Anaya
 */
class GetProductListUseCaseTest {
    companion object {
        internal const val GENERAL_ERROR_MESSAGE = "Hubo un error al realizar la búsqueda. Inténtalo de nuevo"
    }
    private lateinit var getProductListUseCase: GetProductListUseCase

    @Mock
    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getProductListUseCase = GetProductListUseCase(productRepository)
    }

    @Test
    fun `test when get product list and response is successful`() = runBlocking {
        val notEmptyListResponse = ProductFactory.generateProductList(10)
        //GIVEN
        val inputFlow = flowOf(Resource.Success(notEmptyListResponse))
        Mockito.`when`(productRepository.searchProducts("motorola")).thenReturn(inputFlow)
        //WHEN
        val output = getProductListUseCase.execute("motorola").toList()
        //THEN
        assert(output[0].data?.size == 10)
    }

    @Test
    fun `test when get product list and response is successful but get empty product list`() = runBlocking {
        val notEmptyListResponse = ProductFactory.generateProductList(0)
        //GIVEN
        val inputFlow = flowOf(Resource.Success(notEmptyListResponse))
        Mockito.`when`(productRepository.searchProducts("motorola")).thenReturn(inputFlow)
        //WHEN
        val output = getProductListUseCase.execute("motorola").toList()
        //THEN
        assert(output[0].data?.size == 0)
    }

    @Test
    fun `test when get empty product list and response is not successful`() = runBlocking {
        val notEmptyListResponse = ProductFactory.generateProductList(0)

        //GIVEN
        val inputFlow = flowOf(Resource.Error(GENERAL_ERROR_MESSAGE, notEmptyListResponse))
        Mockito.`when`(productRepository.searchProducts("motorola")).thenReturn(inputFlow)

        //WHEN
        val output = getProductListUseCase.execute("motorola")

        // Then
        output.collect { result ->
            assert(result.message == GENERAL_ERROR_MESSAGE)
        }
    }
}