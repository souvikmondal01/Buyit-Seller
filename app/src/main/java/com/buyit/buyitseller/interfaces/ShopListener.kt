package com.buyit.buyitseller.interfaces

import com.buyit.buyitseller.adapters.ShopAdapter.ViewHolder
import com.buyit.buyitseller.models.ShopModel

interface ShopListener {
    fun onOpenClick(shop: ShopModel, holder: ViewHolder)
    fun onCloseClick(shop: ShopModel, holder: ViewHolder)
    fun onShutDownClick(shop: ShopModel, holder: ViewHolder)
    fun onDeleteClick(shop: ShopModel, holder: ViewHolder)
    fun onShopClick(shop: ShopModel, holder: ViewHolder)
    fun viewUpdate(shop: ShopModel, holder: ViewHolder)
}