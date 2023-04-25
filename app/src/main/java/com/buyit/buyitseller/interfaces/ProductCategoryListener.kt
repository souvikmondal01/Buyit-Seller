package com.buyit.buyitseller.interfaces

import com.buyit.buyitseller.adapters.ProductCategoryAdapter
import com.buyit.buyitseller.models.ProductCategory

interface ProductCategoryListener {
    fun productCategoryOnClick(
        holder: ProductCategoryAdapter.ViewHolder,
        position: Int,
        model: ProductCategory
    )
}