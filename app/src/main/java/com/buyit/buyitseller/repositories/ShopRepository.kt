package com.buyit.buyitseller.repositories

import com.buyit.buyitseller.models.ShopModel
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

interface ShopRepository {
    fun fetchShop(): FirestoreRecyclerOptions<ShopModel>
    fun shopStatusUpdate(id: String, status: String)
    fun creteShop(shop: ShopModel)
    fun deleteShop(id: String)
    fun getCategory(result: (ArrayList<String>) -> Unit)
    fun getShopDetails(id: String): Task<DocumentSnapshot>
}