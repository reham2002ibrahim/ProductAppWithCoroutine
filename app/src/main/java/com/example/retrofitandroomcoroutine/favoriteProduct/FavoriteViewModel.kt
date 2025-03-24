package com.example.retrofitandroomcoroutine.favoriteProduct

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.retrofitandroomcoroutine.dataLayer.model.Product
import com.example.retrofitandroomcoroutine.dataLayer.repo.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
class FavoriteViewModel(private val repo: RepositoryInterface) : ViewModel() {

    private val mutableProducts =  MutableLiveData<List<Product>>(emptyList())
    val product: LiveData<List<Product>> = mutableProducts

    private val mutableMessage: MutableLiveData<String> = MutableLiveData()
    val message: LiveData<String> = mutableMessage

    fun getFavoriteProducts() {
        viewModelScope.launch {

                val localProduct = repo.getFavProduct()
            localProduct.collect{
                mutableProducts.value= it



            }


        }
    }

    fun deleteFav(product: Product) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = repo.removeProduct(product)
                if (result > 0) {
                    mutableMessage.postValue("Deleted successfully")
                } else {
                    mutableMessage.postValue("not found product")
                }
            } catch (ex: Exception) {
                mutableMessage.postValue("Error: ${ex.message}")
            }
        }
    }
}