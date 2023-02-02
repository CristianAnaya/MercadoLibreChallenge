package com.cranaya.data.databasemanager

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cranaya.data.BuildConfig.DataBaseName
import com.cranaya.data.databasemanager.dao.ProductSearchHistoryDao
import com.cranaya.data.databasemanager.entity.ProductSearchHistoryEntity

const val DB_VERSION = 1

/**
 * [RoomDatabase] implementation database
 *
 * @author Cristian Anaya
 */
@Database(
    entities = [ProductSearchHistoryEntity::class],
    version = DB_VERSION,
    exportSchema = false
)
abstract class MercadoLibreDatabase: RoomDatabase() {

    abstract fun searchHistoryDao(): ProductSearchHistoryDao

    companion object {

        @Volatile
        private var instance: MercadoLibreDatabase? = null

        fun getInstance(context: Context): MercadoLibreDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): MercadoLibreDatabase {
            return Room.databaseBuilder(context, MercadoLibreDatabase::class.java, DataBaseName)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}