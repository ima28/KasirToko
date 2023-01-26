package com.example.kasirtoko.ui.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kasirtoko.models.Product
import com.example.kasirtoko.repositories.SalesRepository
import com.example.kasirtoko.requests.CreateProductRequest
import com.example.kasirtoko.requests.UpdateProductRequest
import com.example.kasirtoko.vo.ResultWrapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val repository: SalesRepository
) : ViewModel() {

    private val _productsViewModel = MutableLiveData<State>()
    val productsViewModel: LiveData<State> get() = _productsViewModel

    fun getProducts() {
        viewModelScope.launch {
            _productsViewModel.postValue(State.Loading)
            viewModelScope.launch {
                when(val res = repository.getProducts()) {
                    is ResultWrapper.Success<*> -> _productsViewModel.value = State.Success(res.data as List<Product>, res.message)
                    is ResultWrapper.Error<*> -> _productsViewModel.value = State.Failed(res.message)
                }
            }
        }
    }

    fun deleteProduct(productId: Int) {
        viewModelScope.launch {
            _productsViewModel.postValue(State.Loading)
            viewModelScope.launch {
                when(val res = repository.deleteProduct(productId)) {
                    is ResultWrapper.Success<*> -> _productsViewModel.value = State.SuccessDelete(
                        res.data as List<Product>, res.message)
                    is ResultWrapper.Error<*> -> _productsViewModel.value = State.Failed(res.message)
                }
            }
        }
    }

    fun createProduct(request: CreateProductRequest) {
        _productsViewModel.postValue(State.Loading)
        viewModelScope.launch {
            when(val res = repository.createProduct(request)) {
                is ResultWrapper.Success -> _productsViewModel.value = State.SuccessCreate(res.data, res.message)
                is ResultWrapper.Error -> _productsViewModel.value = State.Failed(res.message)
            }
        }
    }

    fun updateProduct(productId: Int?, request: UpdateProductRequest) {
        _productsViewModel.postValue(State.Loading)
        viewModelScope.launch {
            when(val res = repository.updateProduct(productId, request)) {
                is ResultWrapper.Success -> _productsViewModel.value = State.SuccessUpdate(res.data, res.message)
                is ResultWrapper.Error -> _productsViewModel.value = State.Failed(res.message)
            }
        }
    }

    sealed class State {
        data class Success(val data: List<Product>, val message: String): State()
        data class SuccessDelete(val data: List<Product>, val message: String): State()
        data class SuccessUpdate(val data: Product, val message: String): State()
        data class SuccessCreate(val data: Product, val message: String): State()
        data class Failed(val message: String): State()
        object Loading: State()
    }

}