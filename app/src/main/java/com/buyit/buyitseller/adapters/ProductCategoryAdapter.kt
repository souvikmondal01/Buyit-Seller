package com.buyit.buyitseller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buyit.buyitseller.databinding.ListProductCategoryBinding
import com.buyit.buyitseller.interfaces.ProductCategoryListener
import com.buyit.buyitseller.models.ProductCategory
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ProductCategoryAdapter(
    options: FirestoreRecyclerOptions<ProductCategory>,
    private val listener: ProductCategoryListener
) :
    FirestoreRecyclerAdapter<ProductCategory, ProductCategoryAdapter.ViewHolder>(options) {
    class ViewHolder(val binding: ListProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListProductCategoryBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ProductCategory) {
        holder.apply {
            binding.apply {
                tvProductCategory.text = model.category.toString()
            }
            itemView.setOnClickListener {
                listener.productCategoryOnClick(holder, position, model)
            }
        }
    }

}