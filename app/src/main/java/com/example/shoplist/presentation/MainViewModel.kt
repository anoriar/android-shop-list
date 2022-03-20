package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.*

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListRepositoryImpl: ShopListRepositoryImpl = ShopListRepositoryImpl(application)

    private val updateShopItemUseCase: UpdateShopItem = UpdateShopItem(shopListRepositoryImpl)
    private val deleteShopItemUseCase: DeleteShopItem = DeleteShopItem(shopListRepositoryImpl)
    private val getShopItemsUseCase: GetShopItems = GetShopItems(shopListRepositoryImpl)
    private val getShopItemByIdUseCase: GetShopItemById = GetShopItemById(shopListRepositoryImpl)

    var shopListLiveData = getShopItemsUseCase.getShopItems()


    fun getShopItemById(id: Int): ShopItem {
        return getShopItemByIdUseCase.getShopItemById(id)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        updateShopItemUseCase.updateShopItem(newItem)
    }
}