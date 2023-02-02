package com.cranaya.data.databasemanager.dao

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.cranaya.data.databasemanager.MercadoLibreDatabase
import com.cranaya.data.databasemanager.factory.ProductSearchHistoryFactory.generateProductSearchHistoryEntityRandom
import com.google.common.truth.Truth.assertThat
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Integration testing for [ProductSearchHistoryDao]
 *
 * @author Cristian Anaya
 */
@RunWith(AndroidJUnit4::class)
class ProductSearchHistoryDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: MercadoLibreDatabase
    private lateinit var productSearchHistoryDao: ProductSearchHistoryDao

    @Before
    fun setUp(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, MercadoLibreDatabase::class.java).build()
        productSearchHistoryDao = db.searchHistoryDao()
    }

    @After
    fun closeDb(){
        db.close()
    }

    @Test
    fun test_Insert_ProductSearchHistory_Then_Read_ProductSearchHistory() = runBlocking {
        val productSearchHistoryEntity = generateProductSearchHistoryEntityRandom()

        productSearchHistoryDao.insertSearch(productSearchHistoryEntity)

        val productHistorySearch = productSearchHistoryDao.getSearchHistory()

        assertThat(productHistorySearch).isNotNull()
    }

    @Test
    fun test_Update_ProductSearchHistory_Then_Read_ProductSearchHistory() = runBlocking {
        val productSearchHistoryEntity = generateProductSearchHistoryEntityRandom()

        productSearchHistoryDao.updateSearchHistoryByText(
            productSearchHistoryEntity.title,
            productSearchHistoryEntity.timestamp
        )

        val productHistorySearch = productSearchHistoryDao.getSearchHistory()

        assertThat(productHistorySearch).isNotNull()
    }

    @Test
    fun test_GetList_WithTwoItem_ProductHistorySearch() = runBlocking {
        val productSearchHistoryEntity1 = generateProductSearchHistoryEntityRandom()
        val productSearchHistoryEntity2 = generateProductSearchHistoryEntityRandom()

        productSearchHistoryDao.insertSearch(productSearchHistoryEntity1)
        productSearchHistoryDao.insertSearch(productSearchHistoryEntity2)

        val result = productSearchHistoryDao.getSearchHistory()

        assertEquals(2, result.size)
    }
}