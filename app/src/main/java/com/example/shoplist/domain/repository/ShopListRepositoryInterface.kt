package com.example.shoplist.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.ShopItem

interface ShopListRepositoryInterface {
    fun getShopListItems(): LiveData<List<ShopItem>>
    suspend fun getShopItemById(id: Int): ShopItem
    suspend fun addShopItem(shopItem: ShopItem)
    suspend fun updateShopItem(shopItem: ShopItem)
    suspend fun deleteShopItem(shopItem: ShopItem)
}