package com.cranaya.data.factory

import com.cranaya.data.databasemanager.entity.ProductSearchHistoryEntity
import com.cranaya.domain.model.ProductSearchHistoryModel

/**
 * class used to generate objects
 *
 * @author Cristian Anaya
 */

object ProductSearchHistoryFactory {

    fun generateProductSearchHistoryDummyEntities(size: Int): List<ProductSearchHistoryEntity> {
        val mutableProductDtoList = mutableListOf<ProductSearchHistoryEntity>()
        repeat(size) {
            mutableProductDtoList.add(generateProductSearchHistoryEntityRandom())
        }

        return mutableProductDtoList
    }


    fun generateProductSearchHistoryEntityRandom(): ProductSearchHistoryEntity {
        return ProductSearchHistoryEntity(
            title = DataFactory.getRandomString(),
            timestamp = DataFactory.getRandomLong(),
        )
    }

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