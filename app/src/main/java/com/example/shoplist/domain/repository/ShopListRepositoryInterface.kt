package com.example.shoplist.domain.repository

import androidx.lifecycle.LiveData
import com.example.shoplist.domain.ShopItem

interface ShopListRepositoryInterface {
    fun getShopListItems(): LiveData<List<ShopItem>>
    fun getShopItemById(id: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun updateShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}