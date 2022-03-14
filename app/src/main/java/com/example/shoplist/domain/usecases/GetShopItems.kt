package com.example.shoplist.domain.usecases

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class GetShopItems(private val shopListRepositoryInterface: ShopListRepositoryInterface) {

    fun getShopItems(): LiveData<List<ShopItem>> {
        return shopListRepositoryInterface.getShopListItems()
    }
}