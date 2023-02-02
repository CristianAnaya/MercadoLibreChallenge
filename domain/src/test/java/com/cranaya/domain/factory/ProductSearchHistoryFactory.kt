package com.cranaya.domain.factory

import com.cranaya.domain.model.ProductSearchHistoryModel


/**
 * class used to generate objects
 *
 * @author Cristian Anaya
 */
object ProductSearchHistoryFactory {

    fun generateProductSearchHistoryFactoryList(size: Int): List<ProductSearchHistoryModel> {
        val movieList = mutableListOf<ProductSearchHistoryModel>()
        repeat(size) {
            movieList.add(generateProductSearchHistory())
        }
        return movieList
    }

    fun generateProductSearchHistory(): ProductSearchHistoryModel {
        return ProductSearchHistoryModel(
            title = DataFactory.getRandomString(),
            timestamp = DataFactory.getRandomLong(),
        )
    }
}