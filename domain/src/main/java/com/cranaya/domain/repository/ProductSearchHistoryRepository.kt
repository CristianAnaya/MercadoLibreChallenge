package com.cranaya.domain.repository

import com.cranaya.domain.model.Resource
import com.cranaya.domain.model.ProductSearchHistoryModel
import kotlinx.coroutines.flow.Flow

/**
 * Repository for product search history
 *
 * @author Cristian Anaya
 */
interface ProductSearchHistoryRepository {

    /**
     * Save or update the search history by the user in the local database
     *
     * @param productSearchHistory
     */
    suspend fun insertOrUpdateSearchHistory(productSearchHistory: ProductSearchHistoryModel): Int

    /**
     * Check if product already exists in history
     *
     * @param searchText the search query to find matching products
     * @return a [Boolean] that validates if there is a history of the product
     */
    suspend fun existSearch(searchText: String): Boolean

    /**
     * Get a list with the history of products saved in the local database
     *
     * @return a [Resource] instance containing the search result
     */
    suspend fun getSearchHistory(): Flow<Resource<List<ProductSearchHistoryModel>>>

    /**
     * check the size of a string found in the search bar
     *
     * @param searchText the validates the size of the string
     * @return a [Boolean] which checks if it meets the validation
     */
    suspend fun isValidInputSearch(searchText: String): Boolean

    /**
     * check if the search meets the conditions
     *
     * @param searchText capture what the user types in the search bar
     * @return a [Resource] instance containing the output result
     */
    suspend fun isValidSearch(searchText: String): Flow<Resource<Boolean>>

}