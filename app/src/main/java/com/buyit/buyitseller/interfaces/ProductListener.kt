package com.buyit.buyitseller.interfaces

import com.buyit.buyitseller.models.Product

interface ProductListener {

    fun onProductDelete(model: Product)
}