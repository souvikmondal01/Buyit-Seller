package com.buyit.buyitseller.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buyit.buyitseller.repositories.ShopRepository

@Suppress("UNCHECKED_CAST")
class ShopViewModelFactory(private val repository: ShopRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ShopViewModel(repository) as T
    }
}