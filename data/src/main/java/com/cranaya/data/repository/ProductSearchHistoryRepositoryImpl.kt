package com.cranaya.data.repository

import com.cranaya.data.databasemanager.dao.ProductSearchHistoryDao
import com.cranaya.data.databasemanager.mapper.toSearchHistoryEntity
import com.cranaya.data.databasemanager.mapper.toSearchHistoryModel
import com.cranaya.domain.model.Resource
import com.cranaya.domain.utils.interfaces.IErrorLogger
import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.domain.repository.ProductSearchHistoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

/**
 * [ProductSearchHistoryRepository] implementation using a Database
 *
 * @author Cristian Anaya
 */
@Singleton
class ProductSearchHistoryRepositoryImpl @Inject constructor(
    private val productSearchHistoryDao: ProductSearchHistoryDao,
    private val iErrorLogger: IErrorLogger
): ProductSearchHistoryRepository {

    companion object {
        private val TAG : String = ProductSearchHistoryRepositoryImpl::class.java.simpleName
        const val SEARCH_MIN_LENGTH = 2
        const val EXCEPTION_INVALID_SEARCH = "La busqueda no cumple el minimo de caracteres"

    }

    /**
     * See documentation in parent class
     */
    override suspend fun insertOrUpdateSearchHistory(productSearchHistory: ProductSearchHistoryModel): Int {
        return if (!existSearch(productSearchHistory.title)) productSearchHistoryDao.insertSearch(productSearchHistory.toSearchHistoryEntity())?.toInt() ?: 0
        else productSearchHistoryDao.updateSearchHistoryByText(productSearchHistory.title, productSearchHistory.timestamp) ?: 0
    }

    override suspend fun existSearch(searchText: String): Boolean {
        return productSearchHistoryDao.existSearchInHistory(searchText)
    }

    override suspend fun getSearchHistory(): Flow<Resource<List<ProductSearchHistoryModel>>> {
        return flow {
            try {
                emit(Resource.Success(productSearchHistoryDao.getSearchHistory().map { it.toSearchHistoryModel() }))
            } catch(e: IOException) {
                iErrorLogger.logError(TAG, "${e.message}")
            }
        }
    }

    override suspend fun isValidInputSearch(searchText: String): Boolean {
        return searchText.length >= SEARCH_MIN_LENGTH
    }

    override suspend fun isValidSearch(searchText: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                if (!isValidInputSearch(searchText)){
                    emit(Resource.Error(EXCEPTION_INVALID_SEARCH))
                    return@flow
                }
                emit(Resource.Success(isValidInputSearch(searchText)))
            } catch (e: IOException) {
                emit(Resource.Error(e.message!!))
            }
        }
    }
}