package com.example.shoplist.domain.usecases

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class DeleteShopItem(private val shopListRepositoryInterface: ShopListRepositoryInterface) {

    suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListRepositoryInterface.deleteShopItem(shopItem)
    }
}