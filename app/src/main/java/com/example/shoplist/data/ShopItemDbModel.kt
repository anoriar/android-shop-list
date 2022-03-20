package com.example.shoplist.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoplist.domain.ShopItem

@Entity(tableName = "shop_items")
class ShopItemDbModel(
    @PrimaryKey
    val id: Int,
    val name: String,
    val count: Int,
    val enabled: Boolean
)