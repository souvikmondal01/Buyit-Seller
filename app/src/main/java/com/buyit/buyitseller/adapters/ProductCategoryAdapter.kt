package com.buyit.buyitseller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buyit.buyitseller.R
import com.buyit.buyitseller.databinding.ListProductCategoryBinding

class ProductCategoryAdapter(private val list: ArrayList<String>) :
    RecyclerView.Adapter<ProductCategoryAdapter.ViewHolder>() {
    class ViewHolder(val binding: ListProductCategoryBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListProductCategoryBinding.inflate(
            LayoutInflater
                .from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.apply {
            binding.tvProductCategory.text = list[position]
            itemView.setOnClickListener {

            }

        }

    }

    override fun getItemCount(): Int {
        return list.size
    }
}