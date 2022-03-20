package com.example.shoplist.data

import com.example.shoplist.domain.ShopItem

class ShopListMapper {
    fun mapEntityToDbModel(entity: ShopItem): ShopItemDbModel {
        return ShopItemDbModel(
            id = entity.id,
            name = entity.name,
            count = entity.count,
            enabled = entity.enabled
        )
    }

    fun mapDbModelToEntity(dbModel: ShopItemDbModel): ShopItem {
        return ShopItem(
            name = dbModel.name,
            count = dbModel.count,
            enabled = dbModel.enabled,
            id = dbModel.id
        )
    }

    fun mapListDbModelToListEntity(listDbModel: List<ShopItemDbModel>): List<ShopItem> =
        listDbModel.map { mapDbModelToEntity(it) }
}