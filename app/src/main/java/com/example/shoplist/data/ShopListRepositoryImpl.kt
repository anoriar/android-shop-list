package com.example.shoplist.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.repository.ShopListRepositoryInterface

class ShopListRepositoryImpl(application: Application) : ShopListRepositoryInterface {
    private val shopListMapper: ShopListMapper = ShopListMapper()
    private val shopListDao: ShopListDao = AppDatabase.getInstance(application).shopListDao()

    override fun getShopListItems(): LiveData<List<ShopItem>> {
        return Transformations.map(shopListDao.getShopItems()) {
            shopListMapper.mapListDbModelToListEntity(it)
        }
    }

    override suspend fun getShopItemById(id: Int): ShopItem {
        return shopListMapper.mapDbModelToEntity(shopListDao.getShopItemById(id))
    }

    override suspend fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun updateShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }

    override suspend fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }
}