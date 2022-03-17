package com.example.shoplist.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoplist.data.repository.ShopListRepositoryImpl
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.domain.usecases.AddShopItem
import com.example.shoplist.domain.usecases.GetShopItemById
import com.example.shoplist.domain.usecases.UpdateShopItem
import java.lang.Exception

class ShopItemViewModel : ViewModel() {
    private val shopListRepository: ShopListRepositoryImpl = ShopListRepositoryImpl

    private val updateShopItemUseCase: UpdateShopItem = UpdateShopItem(shopListRepository)
    private val addShopItemUseCase: AddShopItem = AddShopItem(shopListRepository)
    private val getShopItemByIdUseCase: GetShopItemById = GetShopItemById(shopListRepository)

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    fun getShopItemById(id: Int): ShopItem {
        return getShopItemByIdUseCase.getShopItemById(id)
    }

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            addShopItemUseCase.addShopItem(ShopItem(name, count, true))
        }
    }

    fun updateShopItem(id: Int, inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val shopItem = getShopItemById(id)
        if (validateInput(name, count)) {
            shopItem.name = name
            shopItem.count = count
            updateShopItemUseCase.updateShopItem(shopItem)
        }
    }

    private fun parseName(inputName: String?): String {
        return inputName?.trim() ?: ""
    }

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputName.value = true
            result = true
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }
}