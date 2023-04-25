package com.buyit.buyitseller.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buyit.buyitseller.databinding.ListProductBinding
import com.buyit.buyitseller.interfaces.ProductListener
import com.buyit.buyitseller.models.Product
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ProductAdapter(options: FirestoreRecyclerOptions<Product>, val listener: ProductListener) :
    FirestoreRecyclerAdapter<Product, ProductAdapter.ViewHolder>(options) {
    class ViewHolder(val binding: ListProductBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListProductBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: Product) {
        holder.apply {
            binding.apply {
                tvProductName.text = model.name
                tvProductPrice.text = "₹" + model.price
                tvProductQuantity.text = model.quantity + " " + model.unit
                tvProductTotalCount.text = model.totalCount
                ivDelete.setOnClickListener {
                    listener.onProductDelete(model)
                }
            }
        }
    }

}