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

    override fun getShopItemById(id: Int): ShopItem {
        return shopListMapper.mapDbModelToEntity(shopListDao.getShopItemById(id))
    }

    override fun addShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }

    override fun updateShopItem(shopItem: ShopItem) {
        shopListDao.addShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }

    override fun deleteShopItem(shopItem: ShopItem) {
        shopListDao.deleteShopItem(shopListMapper.mapEntityToDbModel(shopItem))
    }
}