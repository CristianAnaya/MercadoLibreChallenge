package com.cranaya.data.databasemanager.factory

import com.cranaya.data.databasemanager.entity.ProductSearchHistoryEntity

/**
 * class used to generate objects
 *
 * @author Cristian Anaya
 */

object ProductSearchHistoryFactory {

    fun generateProductSearchHistoryEntityRandom(): ProductSearchHistoryEntity {
        return ProductSearchHistoryEntity(
            title = DataFactory.getRandomString(),
            timestamp = DataFactory.getRandomLong(),
        )
    }
}