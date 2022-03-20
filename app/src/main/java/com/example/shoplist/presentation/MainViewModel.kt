package com.example.shoplist.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoplist.data.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.DeleteShopItem
import com.example.shoplist.domain.usecases.GetShopItems
import com.example.shoplist.domain.usecases.UpdateShopItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val shopListRepositoryImpl: ShopListRepositoryImpl = ShopListRepositoryImpl(application)

    private val updateShopItemUseCase: UpdateShopItem = UpdateShopItem(shopListRepositoryImpl)
    private val deleteShopItemUseCase: DeleteShopItem = DeleteShopItem(shopListRepositoryImpl)
    private val getShopItemsUseCase: GetShopItems = GetShopItems(shopListRepositoryImpl)

    var shopListLiveData = getShopItemsUseCase.getShopItems()


    fun deleteShopItem(shopItem: ShopItem) {
        viewModelScope.launch {
            deleteShopItemUseCase.deleteShopItem(shopItem)
        }
    }

    fun changeEnableState(shopItem: ShopItem) {
        val newItem = shopItem.copy(enabled = !shopItem.enabled)
        viewModelScope.launch {
            updateShopItemUseCase.updateShopItem(newItem)
        }
    }
}