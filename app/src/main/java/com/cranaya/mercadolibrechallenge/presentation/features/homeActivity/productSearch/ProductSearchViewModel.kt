package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productSearch

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cranaya.domain.model.Resource
import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.domain.useCase.productSearch.GetProductSearchHistoryUseCase
import com.cranaya.domain.useCase.productSearch.IsValidSearchUseCase
import com.cranaya.domain.useCase.productSearch.SaveOrUpdateProductHistoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] implementation for products search
 *
 * @author Cristian Anaya
 */
@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val getProductSearchHistoryUseCase: GetProductSearchHistoryUseCase,
    private val isValidSearchUseCase: IsValidSearchUseCase,
    private val saveOrUpdateProductHistoryUseCase: SaveOrUpdateProductHistoryUseCase
): ViewModel() {

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _isValidSearch = MutableLiveData<Boolean>()
    val isValidSearch: LiveData<Boolean> = _isValidSearch

    private val _productsSearchHistory = MutableLiveData<List<ProductSearchHistoryModel>>()
    val productsSearchHistoryModel: LiveData<List<ProductSearchHistoryModel>> = _productsSearchHistory

    var searchText = ""

    fun insertOrUpdateSearch(productSearchHistory: ProductSearchHistoryModel) {
        viewModelScope.launch {
            saveOrUpdateProductHistoryUseCase.execute(productSearchHistory)
        }
    }

    fun getSearchHistory() {
        viewModelScope.launch(Dispatchers.IO) {
            getProductSearchHistoryUseCase
                .execute()
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            _productsSearchHistory.postValue(result.data ?: listOf())
                        }
                        is Resource.Error -> {
                            _error.postValue(result.message!!)
                        }
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

    fun validateSearchText(textToValidate: String) {
        viewModelScope.launch {
            searchText = textToValidate
            isValidSearchUseCase
                .execute(textToValidate)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            _isValidSearch.postValue(result.data ?: false)
                        }
                        is Resource.Error -> {
                            _error.postValue(result.message!!)
                        }
                        is Resource.Loading -> Unit
                    }
                }
        }
    }

    fun resetValues() {
        _isValidSearch.postValue(false)
        _error.postValue("")
    }
}