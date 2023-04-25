package com.buyit.buyitseller.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.buyit.buyitseller.models.Product
import com.buyit.buyitseller.models.ProductCategory
import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.repositories.ShopRepository
import com.google.firebase.firestore.CollectionReference

class ShopViewModel(private val repository: ShopRepository) : ViewModel() {
    private val _category = MutableLiveData<ArrayList<String>>()
    val category: MutableLiveData<ArrayList<String>> get() = _category

    private val _msg = MutableLiveData<String>()
    val msg: MutableLiveData<String> get() = _msg

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

    fun addProductCategory(shopId: String, productCategory: ProductCategory) {
        repository.addProductCategory(shopId, productCategory) {
            _msg.value = it
        }
    }

    fun fetchProductCategory(query: CollectionReference) = repository.fetchProductCategory(query)

    fun addProduct(
        shopId: String,
        productCategoryId: String,
        product: Product,
    ) = repository.addProduct(shopId, productCategoryId, product) {
        _msg.value = it
    }
}