package com.cranaya.domain.useCase.productList

import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import com.cranaya.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * GetProductListUseCase class to execute a user action
 *
 * @param productRepository to abstract access to data
 * @author Cristian Anaya
 */
class GetProductListUseCase @Inject constructor(
    private val productRepository: ProductRepository
) {

    suspend fun execute(productName: String): Flow<Resource<List<Product>>> {
        return productRepository.searchProducts(productName)
    }
}