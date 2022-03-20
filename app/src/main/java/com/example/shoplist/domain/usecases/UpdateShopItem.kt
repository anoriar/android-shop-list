package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class UpdateShopItem(private val shopListRepositoryInterface: ShopListRepositoryInterface) {

    suspend fun updateShopItem(shopItem: ShopItem) {
        shopListRepositoryInterface.updateShopItem(shopItem)
    }
}