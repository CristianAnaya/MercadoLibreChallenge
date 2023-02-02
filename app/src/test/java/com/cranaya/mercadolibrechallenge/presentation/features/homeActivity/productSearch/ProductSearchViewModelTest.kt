package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productSearch

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cranaya.data.repository.ProductSearchHistoryRepositoryImpl
import com.cranaya.data.repository.ProductSearchHistoryRepositoryImpl.Companion.EXCEPTION_INVALID_SEARCH
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import com.cranaya.domain.useCase.productSearch.GetProductSearchHistoryUseCase
import com.cranaya.domain.useCase.productSearch.IsValidSearchUseCase
import com.cranaya.domain.useCase.productSearch.SaveOrUpdateProductHistoryUseCase
import com.cranaya.mercadolibrechallenge.factory.ProductSearchHistoryFactory.generateProductSearchHistoryFactory
import com.cranaya.mercadolibrechallenge.factory.ProductSearchHistoryFactory.generateProductSearchHistoryFactoryList
import com.cranaya.mercadolibrechallenge.utils.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import junit.framework.Assert.assertFalse
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doReturn
import org.mockito.MockitoAnnotations.openMocks

/**
 * Unit tests for [ProductSearchViewModel]
 *
 * @author Cristian Anaya
 */

@ExperimentalCoroutinesApi
class ProductSearchViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var subject: ProductSearchViewModel

    @Mock
    lateinit var getProductSearchHistoryUseCase: GetProductSearchHistoryUseCase

    @Mock
    lateinit var isValidSearchUseCase: IsValidSearchUseCase

    @Mock
    lateinit var saveOrUpdateProductHistoryUseCase: SaveOrUpdateProductHistoryUseCase

    @Mock
    lateinit var searchRepository: ProductSearchHistoryRepository

    @Before
    fun setUp() {
        openMocks(this)
        subject = ProductSearchViewModel(
            getProductSearchHistoryUseCase,
            isValidSearchUseCase,
            saveOrUpdateProductHistoryUseCase
        )

        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun onAfter() {
        Dispatchers.resetMain()
    }

    @Test
    fun `test get list of product history then return quantity`() = runTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Success(generateProductSearchHistoryFactoryList(4)))

        //WHEN
        doReturn(flowQuestions).`when`(getProductSearchHistoryUseCase).execute()
        subject.getSearchHistory()

        //THEN
        val result = subject.productsSearchHistoryModel.getOrAwaitValue()
        assert(result.size == 4)
    }

    @Test
    fun `get the list of product history then return a specific item`() = runTest {
        //GIVEN
        val productHistory1 = generateProductSearchHistoryFactory().copy("Prueba", 3243444332)
        val productHistory2 = generateProductSearchHistoryFactory().copy("Prueba2", 3243444331)

        val flowQuestions = flowOf(Resource.Success(
            listOf(
                productHistory1,
                productHistory2
            )
        ))

        //WHEN
        doReturn(flowQuestions).`when`(getProductSearchHistoryUseCase).execute()
        subject.getSearchHistory()

        //THEN
        val result = subject.productsSearchHistoryModel.getOrAwaitValue().find {
            it.title == "Prueba" && it.timestamp == 3243444332
        }
        assertThat(result != null).isTrue()
    }

    @Test
    fun `test when the search is success`() = runTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Success(true))

        //WHEN
        doReturn(flowQuestions).`when`(isValidSearchUseCase).execute("Prueba")
        subject.validateSearchText("Prueba")

        //THEN
        val result = subject.isValidSearch.getOrAwaitValue()

        assertTrue(result)
    }

    @Test
    fun `test when the search is failed`() = runTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Success(false))

        //WHEN
        doReturn(flowQuestions).`when`(isValidSearchUseCase).execute("Pru")
        subject.validateSearchText("Pru")

        //THEN
        val result = subject.isValidSearch.getOrAwaitValue()

        assertFalse(result)
    }

    @Test
    fun `test when the search is not correct and show error message`() = runTest {
        //GIVEN
        val flowQuestions = flowOf(Resource.Error(EXCEPTION_INVALID_SEARCH, generateProductSearchHistoryFactoryList(0)))

        //WHEN
        doReturn(flowQuestions).`when`(isValidSearchUseCase).execute("Pru")
        subject.validateSearchText("Pru")

        //THEN
        val result = subject.error.getOrAwaitValue()

        assertTrue(result == EXCEPTION_INVALID_SEARCH)
    }
}