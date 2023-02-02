package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cranaya.domain.model.Resource
import com.cranaya.domain.useCase.productList.GetProductListUseCase
import com.cranaya.mercadolibrechallenge.factory.ProductFactory.generateProductList
import com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.home.HomeViewModel
import com.cranaya.mercadolibrechallenge.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations.openMocks

/**
 * Unit tests for [HomeViewModel]
 *
 * @author Cristian Anaya
 */
@ExperimentalCoroutinesApi
class HomeViewModelTest {
    companion object {
        internal const val GENERAL_ERROR_MESSAGE = "Hubo un error al realizar la búsqueda. Inténtalo de nuevo"
    }
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: HomeViewModel

    @Mock
    lateinit var getProductListUseCase: GetProductListUseCase

    @Before
    fun setUp() {
        openMocks(this)
        subject = HomeViewModel(getProductListUseCase)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `get a list of products given the product name successfully`() = runBlocking {
        //GIVEN
        val productName = "Motorola"
        val flowQuestions = flowOf(Resource.Success(generateProductList(4)))

        //WHEN
        doReturn(flowQuestions).`when`(getProductListUseCase).execute(productName)
        subject.searchProductByName(productName)

        //THEN
        val result = subject.products.getOrAwaitValue()

        assert(result.size == 4)
    }

    @Test
    fun `get a list of products given the product name without success and show message`() = runTest {
        //GIVEN
        val productName = "Motorola"

        val flowQuestions = flowOf(Resource.Error(GENERAL_ERROR_MESSAGE, generateProductList(0)))

        //WHEN
        doReturn(flowQuestions).`when`(getProductListUseCase).execute(productName)
        subject.searchProductByName(productName)

        //THEN
        val result = subject.error.getOrAwaitValue()

        assertTrue(result == GENERAL_ERROR_MESSAGE)
    }
}