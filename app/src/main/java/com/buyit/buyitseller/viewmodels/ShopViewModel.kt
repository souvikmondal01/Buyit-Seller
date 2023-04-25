package com.buyit.buyitseller.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.repositories.ShopRepository

class ShopViewModel(private val repository: ShopRepository) : ViewModel() {
    private val _category = MutableLiveData<ArrayList<String>>()
    val category: MutableLiveData<ArrayList<String>> get() = _category
    fun fetchShop() = repository.fetchShop()

    fun shopStatusUpdate(id: String, status: String) {
        repository.shopStatusUpdate(id, status)
    }

    fun createShop(shop: ShopModel) {
        repository.creteShop(shop)
    }

    fun shopDelete(id: String) {
        repository.deleteShop(id)
    }

    fun getCategory() {
        repository.getCategory {
            _category.value = it
        }
    }

    fun getShopDetails(id: String) = repository.getShopDetails(id)

}