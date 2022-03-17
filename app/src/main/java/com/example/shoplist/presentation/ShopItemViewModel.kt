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

    private val _errorInputName = MutableLiveData<Boolean>(false)
    private val _errorInputCount = MutableLiveData<Boolean>(false)
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shopItem: MutableLiveData<ShopItem> = MutableLiveData<ShopItem>()
    val shopItem: LiveData<ShopItem>
        get() = _shopItem

    fun getShopItemById(id: Int) {
        _shopItem.value = getShopItemByIdUseCase.getShopItemById(id)
    }

    private val _shouldClose: MutableLiveData<Unit> = MutableLiveData<Unit>()
    val shouldClose: LiveData<Unit>
        get() = _shouldClose

    fun addShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            addShopItemUseCase.addShopItem(ShopItem(name, count, true))
            finishWork()
        }
    }

    fun updateShopItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        if (validateInput(name, count)) {
            _shopItem.value?.let {
                val item = it.copy(name = name, count = count)
                updateShopItemUseCase.updateShopItem(item)
                finishWork()
            }
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
            _errorInputCount.value = true
            result = false
        }

        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorCountName() {
        _errorInputCount.value = false
    }

    fun finishWork() {
        _shouldClose.value = Unit
    }
}