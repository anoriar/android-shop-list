package com.example.shoplist.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

object ShopListRepositoryImpl : ShopListRepositoryInterface {
    private var shopListLiveData = MutableLiveData<List<ShopItem>>()
    private var shopList = sortedSetOf<ShopItem>({ o1, o2 -> o1.id.compareTo(o2.id) })
    private var autoincrementIndex: Int = 0

    init {
        for (i in 1..20) {
            addShopItem(ShopItem("Item $i", (1..10).random(), true))
        }
        updateShopListLiveData()
    }

    override fun getShopListItems(): LiveData<List<ShopItem>> {
        return shopListLiveData
    }

    override fun getShopItemById(id: Int): ShopItem {
        return shopList.find { it.id == id } ?: throw RuntimeException("Element not found")
    }

    override fun addShopItem(shopItem: ShopItem) {
        if (shopItem.id == ShopItem.UNDEFINED_INDEX) {
            shopItem.id = autoincrementIndex++
        }
        shopList.add(shopItem)
        updateShopListLiveData()
    }

    override fun updateShopItem(shopItem: ShopItem) {
        val oldShopItem = getShopItemById(shopItem.id)
        deleteShopItem(oldShopItem)
        addShopItem(shopItem)
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopList.remove(shopItem)
        updateShopListLiveData()
    }

    private fun updateShopListLiveData() {
        shopListLiveData.value = shopList.toList()
    }
}