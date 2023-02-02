package com.cranaya.data.repository

import com.cranaya.data.requestmanager.dto.ProductResponseDto
import com.cranaya.data.requestmanager.mapper.ProductMapper
import com.cranaya.data.requestmanager.service.ProductService
import com.cranaya.domain.utils.interfaces.IErrorLogger
import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [ProductRepository] implementation using a REST API
 *
 * @author Cristian Anaya
 */
@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val restApi: ProductService,
    private val productMapper: ProductMapper,
    private val iErrorLogger: IErrorLogger
) : ProductRepository {
    companion object {
        private val TAG : String = ProductRepositoryImpl::class.java.simpleName
        internal const val GENERAL_ERROR_MESSAGE = "Hubo un error al realizar la búsqueda. Inténtalo de nuevo"
        internal const val GENERAL_ERROR_NO_RESULTS_FOUND = "No se han encontrado resultados"
        internal const val NETWORK_ERROR = "Revisa tu conexión a internet y vuelve a intentarlo"
        internal const val ERROR_IO_EXCEPTION = "Unexpected error."
        internal const val ERROR_HTTP_EXCEPTION = "Failed to connect to the server."
    }

    /**
     * See documentation in parent class
     */
    override suspend fun searchProducts(query: String): Flow<Resource<List<Product>>> {
        return flow  {
            emit(Resource.Loading(true))
            try {
                val apiResponse = restApi.searchProducts(query = query)
                if (apiResponse?.isSuccessful == true) {
                    emit(buildResultFromSuccessfulApiResponse(apiResponse))
                } else {
                    iErrorLogger.logError(TAG, apiResponse?.message() ?: GENERAL_ERROR_MESSAGE)
                    emit(Resource.Error(apiResponse?.message() ?: GENERAL_ERROR_MESSAGE))
                }
            } catch(e: IOException) {
                iErrorLogger.logError(TAG, "$ERROR_IO_EXCEPTION ${e.message}")
                emit(Resource.Error(NETWORK_ERROR))
            } catch(e: HttpException) {
                iErrorLogger.logError(TAG, "$ERROR_HTTP_EXCEPTION ${e.message}")
                emit(Resource.Error(NETWORK_ERROR))
            }
        }
    }

    /**
     * See documentation in parent class
     */
    override suspend fun getProductDetailById(productId: String): Flow<Resource<Product>> {
        return flow {
            try {
                val apiResponse = restApi.getProductDetail(productId)
                if (apiResponse?.isSuccessful == true) {
                    val product = productMapper.map(apiResponse.body())
                    emit(Resource.Success(product))
                } else {
                    iErrorLogger.logError(TAG, apiResponse?.message() ?: GENERAL_ERROR_MESSAGE)
                    emit(Resource.Error(apiResponse?.message() ?: GENERAL_ERROR_MESSAGE))
                }
            } catch(e: IOException) {
                iErrorLogger.logError(TAG, "$ERROR_IO_EXCEPTION ${e.message}")
                emit(Resource.Error(NETWORK_ERROR))
            } catch(e: HttpException) {
                iErrorLogger.logError(TAG, "$ERROR_HTTP_EXCEPTION ${e.message}")
                emit(Resource.Error(NETWORK_ERROR))
            }
        }
    }

    private fun buildResultFromSuccessfulApiResponse(apiResponse: Response<ProductResponseDto?>): Resource<List<Product>> {

        val responseBody = apiResponse.body()

        val searchResults = responseBody?.results?.mapNotNull(productMapper::map)

        return if (searchResults != null) {
            buildResponseFromNonNullResult(searchResults)
        } else {
            Resource.Error(GENERAL_ERROR_NO_RESULTS_FOUND)
        }
    }

    private fun buildResponseFromNonNullResult(
        products: List<Product>,
    ): Resource<List<Product>> {
        return if (products.isEmpty()) {
            Resource.Error(GENERAL_ERROR_NO_RESULTS_FOUND)
        } else {
            Resource.Success(products)
        }
    }
}
