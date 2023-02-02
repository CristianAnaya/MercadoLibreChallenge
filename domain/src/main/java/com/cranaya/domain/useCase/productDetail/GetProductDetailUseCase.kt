package com.cranaya.domain.useCase.productDetail

import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetProductDetailUseCase class to execute a user action
 *
 * @param productRepository to abstract access to data
 * @author Cristian Anaya
 */
class GetProductDetailUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun execute(productId: String): Flow<Resource<Product>> {
        return productRepository.getProductDetailById(productId)
    }

}