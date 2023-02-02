package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.home

import androidx.lifecycle.*
import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import com.cranaya.domain.useCase.productList.GetProductListUseCase
import com.cranaya.mercadolibrechallenge.core.helpers.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [ViewModel] implementation for show list products search
 *
 * @author Cristian Anaya
 */
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductListUseCase: GetProductListUseCase,
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = SingleLiveEvent<String>()
    val error: LiveData<String> get() = _error

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    private val _searchText = MutableLiveData<String>()
    val searchText: LiveData<String> = _searchText


    fun searchProductByName(productName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            getProductListUseCase
                .execute(productName)
                .collect { result ->
                    when(result) {
                        is Resource.Success -> {
                            _searchText.postValue(productName)
                            _isLoading.postValue(false)
                            _products.postValue(result.data ?: listOf())
                        }

                        is Resource.Error -> {
                            _isLoading.postValue(false)
                            _error.postValue(result.message ?: "")
                        }

                        is Resource.Loading -> _isLoading.postValue(result.isLoading)
                    }
                }
        }
    }

}