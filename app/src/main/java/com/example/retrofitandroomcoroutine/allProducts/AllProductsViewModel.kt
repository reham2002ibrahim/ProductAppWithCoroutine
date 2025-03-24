package com.example.retrofitandroomcoroutine.allProducts

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import com.example.retrofitandroomcoroutine.dataLayer.model.Response
import com.example.retrofitandroomcoroutine.dataLayer.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AllProductsViewModel(private val repo: RepositoryInterface) : ViewModel() {

    private val _products = MutableStateFlow<Response>(Response.Loading)
    val products: StateFlow<Response> = _products.asStateFlow()

    private val _message = MutableStateFlow("")
    val message: StateFlow<String> = _message.asStateFlow()

    init {
        getProducts()
    }

    fun getProducts() {
        viewModelScope.launch(Dispatchers.IO) {
            _products.value = Response.Loading
            try {
                val productList = repo.getAllProduct(true)
                _products.value = Response.Success(productList ?: emptyList())
            } catch (ex: Exception) {
                _products.value = Response.Failure(ex)
                _message.value = "Error: ${ex.message}"
            }
        }
    }

    fun addToFavorite(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.addProduct(product)
                _message.value = if (result > 0) {
                    "Added successfully"
                } else {
                    "Already in favorites"
                }
            } catch (ex: Exception) {
                _message.value = "Failed to add: ${ex.message}"
            }
        }
    }
}
