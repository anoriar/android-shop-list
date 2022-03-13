package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class GetShopItems(private val shopListRepositoryInterface: ShopListRepositoryInterface) {

    fun getShopItems(): List<ShopItem> {
        return shopListRepositoryInterface.getShopListItems()
    }
}