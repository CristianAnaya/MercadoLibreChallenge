package com.cranaya.data.databasemanager.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Model classes for a product search history in the database
 *
 * @author Cristian Anaya
 */
@Entity
data class ProductSearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String = "",
    var timestamp: Long = 0L
)