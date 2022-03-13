package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class AddShopItem(private val shopListRepositoryInterface: ShopListRepositoryInterface) {

    fun addShopItem(shopItem: ShopItem) {
        shopListRepositoryInterface.addShopItem(shopItem)
    }
}