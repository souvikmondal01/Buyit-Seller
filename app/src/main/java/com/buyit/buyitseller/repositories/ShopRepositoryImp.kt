package com.buyit.buyitseller.repositories

import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.utils.CommonUtils.db
import com.buyit.buyitseller.utils.Constant.PENDING
import com.buyit.buyitseller.utils.Constant.SHOP
import com.buyit.buyitseller.utils.Constant.SHUT_DOWN
import com.buyit.buyitseller.utils.Constant.STATUS
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot

class ShopRepositoryImp : ShopRepository {
    private val collection = db.collection(SHOP)

    override fun fetchShop(): FirestoreRecyclerOptions<ShopModel> {
        return FirestoreRecyclerOptions.Builder<ShopModel>().setQuery(
            collection,
            ShopModel::class.java
        ).build()
    }

    override fun shopStatusUpdate(id: String, status: String) {
        collection.document(id).update(STATUS, status)
    }

    override fun creteShop(shop: ShopModel) {
        val id = collection.document().id
        shop.id = id
        shop.verificationStatus = PENDING
        shop.status = SHUT_DOWN
        collection.document(id).set(shop)
    }

    override fun deleteShop(id: String) {
        collection.document(id).delete()
    }

    override fun getCategory(result: (ArrayList<String>) -> Unit) {

        db.collection("more").document("shopCategory").collection("list").get()
            .addOnSuccessListener {
                val categories = arrayListOf<String>()
                for (document in it) {
                    categories.add(document.data["categoryName"].toString())
                }
                result.invoke(
                    categories
                )
            }
    }

    override fun getShopDetails(id: String): Task<DocumentSnapshot> {
        return db.collection(SHOP).document(id).get()
    }

}