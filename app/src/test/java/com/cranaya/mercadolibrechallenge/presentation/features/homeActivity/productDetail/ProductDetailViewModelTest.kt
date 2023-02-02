package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productDetail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cranaya.domain.model.Resource
import com.cranaya.domain.useCase.productDetail.GetProductDetailUseCase
import com.cranaya.mercadolibrechallenge.factory.ProductFactory.generateProduct
import com.cranaya.mercadolibrechallenge.utils.getOrAwaitValue
import com.google.common.truth.Truth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [ProductDetailViewModel]
 *
 * @author Cristian Anaya
 */
@ExperimentalCoroutinesApi
class ProductDetailViewModelTest {
    companion object {
        internal const val GENERAL_ERROR_MESSAGE = "Hubo un error al realizar la búsqueda. Inténtalo de nuevo"
    }

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: ProductDetailViewModel

    @Mock
    lateinit var getProductDetailUseCase: GetProductDetailUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        subject = ProductDetailViewModel(getProductDetailUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get product detail product given the product id then response successfully`() = runBlocking {
        //GIVEN
        val productId = "D434343SB"
        val flowQuestions = flowOf(Resource.Success(generateProduct().copy(id = productId)))

        //WHEN
        Mockito.doReturn(flowQuestions).`when`(getProductDetailUseCase).execute(productId)
        subject.getProductDetailById(productId)

        //THEN
        val result = subject.productModel.getOrAwaitValue()

        Truth.assertThat(result != null).isTrue()
    }

    @Test
    fun `test get product given the product id is not successful then show message`() = runBlocking {
        //GIVEN
        val productId = "D434343SB"
        val flowQuestions = flowOf(Resource.Error(GENERAL_ERROR_MESSAGE, generateProduct()))

        //WHEN
        Mockito.doReturn(flowQuestions).`when`(getProductDetailUseCase).execute(productId)
        subject.getProductDetailById(productId)

        //THEN
        val result = subject.error.getOrAwaitValue()

        Assert.assertTrue(result == GENERAL_ERROR_MESSAGE)
    }
}