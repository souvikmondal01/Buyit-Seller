package com.buyit.buyitseller.repositories

import com.buyit.buyitseller.models.Product
import com.buyit.buyitseller.models.ProductCategory
import com.buyit.buyitseller.models.ShopModel
import com.buyit.buyitseller.utils.CommonUtils.db
import com.buyit.buyitseller.utils.Constant
import com.buyit.buyitseller.utils.Constant.PENDING
import com.buyit.buyitseller.utils.Constant.SHOP
import com.buyit.buyitseller.utils.Constant.SHUT_DOWN
import com.buyit.buyitseller.utils.Constant.STATUS
import com.buyit.buyitseller.utils.Constant.SUCCESS
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

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

    override fun addProductCategory(
        shopId: String,
        productCategory: ProductCategory,
        msg: (String) -> Unit
    ) {

        GlobalScope.launch {
            try {
                val dbRef =
                    db.collection(SHOP).document(shopId)
                        .collection(Constant.PRODUCT)
                val id = dbRef.document().id
                productCategory.id = id
                dbRef.document(id).set(productCategory).await()
                withContext(Dispatchers.Main) {
                    msg.invoke("added successfully")
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    msg.invoke(e.message.toString())
                }
            }
        }
    }

    override fun fetchProductCategory(query: CollectionReference): FirestoreRecyclerOptions<ProductCategory> {
        return FirestoreRecyclerOptions.Builder<ProductCategory>().setQuery(
            query,
            ProductCategory::class.java
        ).build()
    }

    override fun addProduct(
        shopId: String,
        productCategoryId: String,
        product: Product,
        msg: (String) -> Unit
    ) {
        GlobalScope.launch {
            try {
                val dbRef =
                    db.collection(SHOP).document(shopId).collection(
                        Constant.PRODUCT
                    ).document(productCategoryId).collection(Constant.PRODUCT)
                val id = dbRef.document().id
                product.id = id
                dbRef.document(id).set(product).await()
                withContext(Dispatchers.Main) {
                    msg.invoke(SUCCESS)
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    msg.invoke(e.message.toString())
                }
            }
        }
    }

}