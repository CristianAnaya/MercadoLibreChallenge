package com.cranaya.domain.useCase.productDetail

import com.cranaya.domain.factory.ProductFactory
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductRepository
import com.cranaya.domain.useCase.productList.GetProductListUseCaseTest
import junit.framework.TestCase.assertNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks

/**
 * Unit tests for [GetProductDetailUseCase]
 *
 * @author Cristian Anaya
 */
class GetProductDetailUseCaseTest {
    companion object {
        internal const val GENERAL_ERROR_MESSAGE = "Hubo un error al realizar la búsqueda. Inténtalo de nuevo"
    }
    private lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @Mock
    lateinit var productRepository: ProductRepository

    @Before
    fun setUp() {
        openMocks(this)
        getProductDetailUseCase = GetProductDetailUseCase(productRepository)
    }

    @Test
    fun `test when get product detail given product id and response is successful`() = runBlocking {
        val productId = "32"
        val notEmptyResponse = ProductFactory.generateProduct()

        //GIVEN
        val inputFlow = flowOf(Resource.Success(notEmptyResponse))
        `when`(productRepository.getProductDetailById(productId)).thenReturn(inputFlow)

        //WHEN
        val output = getProductDetailUseCase.execute(productId)

        //THEN
        output.collect { result ->
            assertNotNull(result.data)
        }
    }

    @Test
    fun `test when get product detail given product id and response not successful`() = runBlocking {
        val productId = "32"
        val notEmptyResponse = ProductFactory.generateProduct()

        // GIVEN
        val inputFlow = flowOf(Resource.Error(GENERAL_ERROR_MESSAGE, notEmptyResponse))
        `when`(productRepository.getProductDetailById(productId)).thenReturn(inputFlow)

        // WHEN
        val output = getProductDetailUseCase.execute(productId)

        // THEN
        output.collect { result ->
            assert(result.message == GENERAL_ERROR_MESSAGE)
        }
    }
}