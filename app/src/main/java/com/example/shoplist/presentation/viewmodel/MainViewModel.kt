package com.example.shoplist.presentation.viewmodel

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

    var shopListLiveData = MutableLiveData<List<ShopItem>>()
    var shopItemLiveData = MutableLiveData<ShopItem>()

    fun getShopListItems() {
        val list = getShopItemsUseCase.getShopItems()
        shopListLiveData.value = list
    }

    fun getShopItemById(id: Int) {
        shopItemLiveData.value = getShopItemByIdUseCase.getShopItemById(id)
    }

    fun addShopItem(shopItem: ShopItem) {
        addShopItemUseCase.addShopItem(shopItem)
        getShopListItems()
    }

    fun updateShopItem(shopItem: ShopItem) {
        updateShopItemUseCase.updateShopItem(shopItem)
        getShopListItems()
    }

    fun deleteShopItem(shopItem: ShopItem) {
        deleteShopItemUseCase.deleteShopItem(shopItem)
        getShopListItems()
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        updateShopItemUseCase.updateShopItem(newItem)
        getShopListItems()
    }
}