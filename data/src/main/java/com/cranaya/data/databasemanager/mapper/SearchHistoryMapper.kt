package com.cranaya.data.databasemanager.mapper

import com.cranaya.data.databasemanager.entity.ProductSearchHistoryEntity
import com.cranaya.data.requestmanager.dto.ProductDto
import com.cranaya.domain.model.Product
import com.cranaya.domain.model.ProductSearchHistoryModel

/**
 * Function to transform a [ProductSearchHistoryEntity] instance into a [ProductSearchHistoryModel] instance
 *
 * @author Cristian Anaya
 */
fun ProductSearchHistoryEntity.toSearchHistoryModel(): ProductSearchHistoryModel {
    return ProductSearchHistoryModel(
        title = title
    )
}

/**
 * Function to transform a [ProductSearchHistoryModel] instance into a [ProductSearchHistoryEntity] instance
 *
 * @author Cristian Anaya
 */
fun ProductSearchHistoryModel.toSearchHistoryEntity(): ProductSearchHistoryEntity {
    return ProductSearchHistoryEntity(
        title = title
    )
}