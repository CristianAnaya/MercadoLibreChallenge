package com.cranaya.data.databasemanager.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cranaya.data.databasemanager.entity.ProductSearchHistoryEntity

/**
 * interface to perform different actions on the [ProductSearchHistoryDao] database
 * to the entity [ProductSearchHistoryEntity]
 *
 * @author Cristian Anaya
 */
@Dao
interface ProductSearchHistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(searchHistory: ProductSearchHistoryEntity): Long?

    @Query("SELECT * FROM ProductSearchHistoryEntity ORDER BY timestamp DESC LIMIT 5")
    suspend fun getSearchHistory(): List<ProductSearchHistoryEntity>

    @Query("SELECT EXISTS(SELECT * FROM ProductSearchHistoryEntity WHERE title = :productName)")
    suspend fun existSearchInHistory(productName: String) : Boolean

    @Query("UPDATE ProductSearchHistoryEntity SET timestamp = :timestamp WHERE title = :searchText")
    suspend fun updateSearchHistoryByText(searchText: String, timestamp: Long): Int?

}