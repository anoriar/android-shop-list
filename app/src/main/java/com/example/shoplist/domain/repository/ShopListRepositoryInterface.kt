package com.example.shoplist.domain.repository

import com.example.shoplist.domain.ShopItem

interface ShopListRepositoryInterface {
    fun getShopListItems(): List<ShopItem>
    fun getShopItemById(id: Int): ShopItem
    fun addShopItem(shopItem: ShopItem)
    fun updateShopItem(shopItem: ShopItem)
    fun deleteShopItem(shopItem: ShopItem)
}