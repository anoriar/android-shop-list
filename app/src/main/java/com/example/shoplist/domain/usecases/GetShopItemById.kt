package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class GetShopItemById(private val shopListRepositoryInterface: ShopListRepositoryInterface) {
    suspend fun getShopItemById(id: Int): ShopItem {
        return shopListRepositoryInterface.getShopItemById(id)
    }
}