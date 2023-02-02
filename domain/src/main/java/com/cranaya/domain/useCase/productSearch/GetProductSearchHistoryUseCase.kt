package com.cranaya.domain.useCase.productSearch

import com.cranaya.domain.model.Resource
import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * IsValidSearchUseCase class to execute a user action
 *
 * @param productSearchHistoryRepository to abstract access to data
 * @author Cristian Anaya
 */
class GetProductSearchHistoryUseCase @Inject constructor(
    private val productSearchHistoryRepository: ProductSearchHistoryRepository
) {

    suspend fun execute(): Flow<Resource<List<ProductSearchHistoryModel>>> {
        return productSearchHistoryRepository.getSearchHistory()
    }
}