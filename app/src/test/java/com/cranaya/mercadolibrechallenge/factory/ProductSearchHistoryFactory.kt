package com.cranaya.mercadolibrechallenge.factory

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
            movieList.add(generateProductSearchHistoryFactory())
        }
        return movieList
    }

    fun generateProductSearchHistoryFactory(): ProductSearchHistoryModel {
        return ProductSearchHistoryModel(
            title = DataFactory.getRandomString(),
            timestamp = DataFactory.getRandomLong(),
        )
    }
}