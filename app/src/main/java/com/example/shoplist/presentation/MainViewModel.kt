package com.example.shoplist.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.repository.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.*

class MainViewModel : ViewModel() {

    private val shopListRepositoryImpl: ShopListRepositoryImpl = ShopListRepositoryImpl

    private val addShopItemUseCase: AddShopItem = AddShopItem(shopListRepositoryImpl)
    private val updateShopItemUseCase: UpdateShopItem = UpdateShopItem(shopListRepositoryImpl)
    private val deleteShopItemUseCase: DeleteShopItem = DeleteShopItem(shopListRepositoryImpl)
    private val getShopItemsUseCase: GetShopItems = GetShopItems(shopListRepositoryImpl)
    private val getShopItemByIdUseCase: GetShopItemById = GetShopItemById(shopListRepositoryImpl)

    var shopListLiveData = getShopItemsUseCase.getShopItems()


    fun getShopItemById(id: Int): ShopItem {
        return getShopItemByIdUseCase.getShopItemById(id)
    }

    fun addShopItem(shopItem: ShopItem) {
        addShopItemUseCase.addShopItem(shopItem)
    }

    fun updateShopItem(shopItem: ShopItem) {
        updateShopItemUseCase.updateShopItem(shopItem)
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        updateShopItemUseCase.updateShopItem(newItem)
    }
}