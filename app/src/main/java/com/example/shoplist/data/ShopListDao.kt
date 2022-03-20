package com.example.shoplist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.shoplist.domain.ShopItem

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopItems(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_items WHERE id = :id LIMIT 1")
    fun getShopItemById(id: Int): ShopItemDbModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem(shopItem: ShopItemDbModel)

    @Delete
    fun deleteShopItem(shopItem: ShopItemDbModel)
}