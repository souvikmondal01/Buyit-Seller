package com.buyit.buyitseller.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.buyit.buyitseller.databinding.ListShopBinding
import com.buyit.buyitseller.interfaces.ShopListener
import com.buyit.buyitseller.models.ShopModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions

class ShopAdapter(
    options: FirestoreRecyclerOptions<ShopModel>,
    private val listener: ShopListener
) :
    FirestoreRecyclerAdapter<ShopModel, ShopAdapter.ViewHolder>(options) {

    class ViewHolder(val binding: ListShopBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListShopBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: ShopModel) {
        holder.apply {
            binding.apply {
                tvShopName.text = model.shopName
                tvShopCategory.text = model.shopCategory
                btnOpen.setOnClickListener { listener.onOpenClick(model, holder) }
                btnClose.setOnClickListener { listener.onCloseClick(model, holder) }
                btnShutDown.setOnClickListener { listener.onShutDownClick(model, holder) }
                btnDelete.setOnClickListener { listener.onDeleteClick(model, holder) }
            }
            itemView.setOnClickListener { listener.onShopClick(model, holder) }
        }
        listener.viewUpdate(model, holder)
    }


}