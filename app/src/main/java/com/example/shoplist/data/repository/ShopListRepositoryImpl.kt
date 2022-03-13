package com.example.shoplist.data.repository

import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

object ShopListRepositoryImpl : ShopListRepositoryInterface {
    private var shopList = mutableListOf<ShopItem>()
    private var autoincrementIndex: Int = 0

    init {
        for (i in 1..10) {
            shopList.add(ShopItem("Item $i", (1..10).random(), true))
        }
    }

    override fun getShopListItems(): List<ShopItem> {
        return shopList.toList()
    }

    override fun getShopItemById(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopItem.id = autoincrementIndex++
        shopList.add(shopItem)
    }

    override fun updateShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        deleteShopItem(oldShopItem)
        shopList.add(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
    }
}