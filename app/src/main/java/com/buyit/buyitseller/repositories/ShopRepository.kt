package com.buyit.buyitseller.repositories

import com.buyit.buyitseller.models.Product
import com.buyit.buyitseller.models.ProductCategory
import com.buyit.buyitseller.models.ShopModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot

interface ShopRepository {
    fun fetchShop(): FirestoreRecyclerOptions<ShopModel>
    fun shopStatusUpdate(id: String, status: String)
    fun creteShop(shop: ShopModel)
    fun deleteShop(id: String)
    fun getCategory(result: (ArrayList<String>) -> Unit)
    fun getShopDetails(id: String): Task<DocumentSnapshot>
    fun addProductCategory(shopId: String, productCategory: ProductCategory, msg: (String) -> Unit)
    fun fetchProductCategory(query: CollectionReference): FirestoreRecyclerOptions<ProductCategory>
    fun addProduct(
        shopId: String,
        productCategoryId: String,
        product: Product,
        msg: (String) -> Unit
    )

    fun fetchProduct(query: CollectionReference): FirestoreRecyclerOptions<Product>
}