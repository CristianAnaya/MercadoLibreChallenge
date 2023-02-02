package com.cranaya.mercadolibrechallenge.presentation.features.homeActivity.productDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Resource
import com.cranaya.domain.useCase.productDetail.GetProductDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
/**
 * [ViewModel] implementation for show product detail
 *
 * @author Cristian Anaya
 */
@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val getProductDetailUseCase: GetProductDetailUseCase
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    private val _productModel = MutableLiveData<Product?>()
    val productModel: LiveData<Product?> = _productModel

    fun getProductDetailById(productId: String) {
        viewModelScope.launch {
            viewModelScope.launch {
                getProductDetailUseCase
                    .execute(productId)
                    .collect { result ->
                        when(result) {
                            is Resource.Success -> {
                                _productModel.postValue(result.data!!)
                                _isLoading.postValue(false)
                            }

                            is Resource.Error -> {
                                _error.postValue(result.message!!)
                                _isLoading.postValue(false)
                            }

                            is Resource.Loading -> _isLoading.postValue(result.isLoading)
                        }
                    }
            }
        }
    }

}