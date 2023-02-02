package com.cranaya.data.repository

import com.cranaya.data.repository.ProductRepositoryImpl.Companion.GENERAL_ERROR_MESSAGE
import com.cranaya.data.requestmanager.dto.ProductDto
import com.cranaya.data.requestmanager.dto.ProductResponseDto
import com.cranaya.data.requestmanager.mapper.ProductMapper
import com.cranaya.data.requestmanager.service.ProductService
import com.cranaya.domain.utils.interfaces.IErrorLogger
import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations.openMocks
import retrofit2.Response

/**
 * Unit tests for [ProductRepositoryImpl]
 *
 * @author Cristian Anaya
 */
class ProductRepositoryImplTest {

    @InjectMocks
    private lateinit var subject: ProductRepositoryImpl

    @Mock
    private lateinit var restApi: ProductService

    @Mock
    private lateinit var productMapper: ProductMapper

    @Mock
    private lateinit var errorLogger: IErrorLogger

    @Mock
    private lateinit var response: Response<ProductResponseDto?>

    @Mock
    private lateinit var responseProductDto: Response<ProductDto?>

    @Mock
    private lateinit var apiResponse: Response<ProductResponseDto?>

    @Mock
    private lateinit var apiResponseProductDto: Response<ProductDto?>

    @Mock
    private lateinit var productDto: ProductDto

    @Mock
    private lateinit var product: Product

    @Mock
    private lateinit var apiErrorResponseBody: ResponseBody

    @Before
    @Throws(Exception::class)
    fun setUp() {
        openMocks(this)
    }

    @Test
    fun `test searchProducts given response is successful and dto is mapped then return result with ResourceSuccess`() =
        runBlocking {
            `when`(restApi.searchProducts(query = "motorola"))
                .thenReturn(response)
            `when`(apiResponse.isSuccessful).thenReturn(true)
            `when`(apiResponse.body()).thenReturn(ProductResponseDto(listOf(productDto)))
            `when`(productMapper.map(productDto)).thenReturn(product)

            subject.searchProducts("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> assertEquals(product, result.data)

                    is Resource.Error -> Unit

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test SearchProducts given response is successful and no dto is mapped then return result without ResourceSuccess`() =
        runBlocking {
            `when`(restApi.searchProducts(query = "motorola"))
                .thenReturn(response as Response<ProductResponseDto?>?)
            `when`(apiResponse.isSuccessful).thenReturn(true)
            `when`(apiResponse.body())
                .thenReturn(ProductResponseDto(listOf(productDto)))
            `when`(productMapper.map(productDto)).thenReturn(null)

            subject.searchProducts("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> Unit

                    is Resource.Error -> assertEquals(GENERAL_ERROR_MESSAGE, result.message)

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test search products given response is successful and result field In response is null then return result without ResourceSuccess`() =
        runBlocking {
            `when`(restApi.searchProducts(query = "motorola"))
                .thenReturn(response as Response<ProductResponseDto?>?)
            `when`(apiResponse.isSuccessful).thenReturn(true)
            `when`(apiResponse.body()).thenReturn(ProductResponseDto(null))

            subject.searchProducts("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> Unit

                    is Resource.Error -> assertEquals(GENERAL_ERROR_MESSAGE, result.message)

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test search products given rest api returns null call then return result without ResourceSuccess`() =
        runBlocking {
            `when`(restApi.searchProducts(query = "motorola" ))
                .thenReturn(null)

            subject.searchProducts("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> Unit

                    is Resource.Error -> assertEquals(GENERAL_ERROR_MESSAGE, result.message)

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test search products given rest api returns not successful call then return result without ResourceSuccess`() =
        runBlocking {
            `when`(restApi.searchProducts(query = "motorola"))
                .thenReturn(response as Response<ProductResponseDto?>?)
            `when`(apiResponse.isSuccessful).thenReturn(false)
            `when`(apiResponse.errorBody()).thenReturn(apiErrorResponseBody)
            `when`(apiErrorResponseBody.toString()).thenReturn("{Api Response body}")

            subject.searchProducts("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> Unit

                    is Resource.Error -> assertEquals(GENERAL_ERROR_MESSAGE, result.message)

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test get product details given response is successful and dto is mapped then return result with ResourceSuccess`() =
        runBlocking {
            `when`(restApi.getProductDetail(itemId = "MLU613494835"))
                .thenReturn(responseProductDto)
            `when`(apiResponseProductDto.isSuccessful).thenReturn(true)
            `when`(apiResponseProductDto.body()).thenReturn(productDto)
            `when`(productMapper.map(apiResponseProductDto.body())).thenReturn(product)

            subject.getProductDetailById("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> assertEquals(product, result.data)

                    is Resource.Error -> Unit

                    is Resource.Loading -> Unit
                }
            }
    }

    @Test
    fun `test get product details given rest api returns not successful call then return result without ResourceSuccess`() =
        runBlocking {
            `when`(restApi.getProductDetail(itemId = "MLU613494835"))
                .thenReturn(responseProductDto)
            `when`(apiResponse.isSuccessful).thenReturn(false)
            `when`(apiResponse.errorBody()).thenReturn(apiErrorResponseBody)
            `when`(apiErrorResponseBody.toString()).thenReturn("{Api Response body}")

            subject.getProductDetailById("motorola").collect { result ->
                when(result) {
                    is Resource.Success -> Unit

                    is Resource.Error -> assertEquals(GENERAL_ERROR_MESSAGE, result.message)

                    is Resource.Loading -> Unit
                }
            }
    }
}