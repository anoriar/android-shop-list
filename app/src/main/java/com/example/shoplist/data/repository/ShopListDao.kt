package com.example.shoplist.data.repository

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ShopListDao {

    @Query("SELECT * FROM shop_items")
    fun getShopItems(): LiveData<List<ShopItemDbModel>>

    @Query("SELECT * FROM shop_items WHERE id = :id LIMIT 1")
    fun getShopItemById(id: Int): LiveData<ShopItemDbModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addShopItem()

    @Delete
    fun deleteShopItem()
}