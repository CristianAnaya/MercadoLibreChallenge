package com.cranaya.domain.useCase.productSearch

import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import javax.inject.Inject

/**
 * SaveOrUpdateProductHistoryUseCase class to execute a user action
 *
 * @param productSearchHistoryRepository to abstract access to data
 * @author Cristian Anaya
 */
class SaveOrUpdateProductHistoryUseCase @Inject constructor(
    private val productSearchHistoryRepository: ProductSearchHistoryRepository
) {
    suspend fun execute(productSearchHistoryModel: ProductSearchHistoryModel): Int {
        return productSearchHistoryRepository.insertOrUpdateSearchHistory(productSearchHistoryModel)
    }
}