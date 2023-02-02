package com.cranaya.domain.useCase.productSearch

import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import com.cranaya.domain.useCase.productSearch.IsValidSearchUseCase
import junit.framework.TestCase.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks

/**
 * Unit tests for [IsValidSearchUseCase]
 *
 * @author Cristian Anaya
 */
class IsValidSearchUseCaseTest {
    private lateinit var isValidSearchUseCase: IsValidSearchUseCase

    @Mock
    lateinit var searchRepository: ProductSearchHistoryRepository

    @Before
    fun setUp() {
        openMocks(this)
        isValidSearchUseCase = IsValidSearchUseCase(searchRepository)
    }

    @Test
    fun `test product search given a text is valid and then return result true`() = runBlocking {
        val searchText = "Test"
        // Given
        val inputFlow = flowOf((Resource.Success(true)))
        `when`(searchRepository.isValidSearch(searchText)).thenReturn(inputFlow)

        // When
        val output = isValidSearchUseCase.execute(searchText)

        // Then
        output.collect { result ->
            assertTrue(result.data!!)
        }
    }

    @Test
    fun `test product search given a text is invalid and then return result false`() = runBlocking {
        val searchText = "Tes"
        // Given
        val inputFlow = flowOf((Resource.Success(false)))
        `when`(searchRepository.isValidSearch(searchText)).thenReturn(inputFlow)

        // When
        val output = isValidSearchUseCase.execute(searchText)

        // Then
        output.collect { result ->
            assertFalse(result.data!!)
        }
    }

}