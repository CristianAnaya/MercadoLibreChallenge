package com.cranaya.domain.repository

import com.cranaya.domain.model.Resource
import com.cranaya.domain.model.Product
import kotlinx.coroutines.flow.Flow

/**
 * Repository for products
 *
 * @author Cristian Anaya
 */
interface ProductRepository {

    /**
     * Search products matching the given query, limited by the given page size and starting at
     * the given offset
     *
     * @param query the search query to find matching products
     */
    suspend fun searchProducts(query: String): Flow<Resource<List<Product>>>


    /**
     * We look for the detail of the product that was selected
     *
     * @param productId to consult the detail of a specific product
     */
    suspend fun getProductDetailById(productId: String): Flow<Resource<Product>>
}
