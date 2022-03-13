package com.example.shoplist.domain

data class ShopItem(
    var name: String,
    var count: Int,
    var enabled: Boolean,
    var id: Int = UNDEFINED_INDEX
) {

    companion object {
        const val UNDEFINED_INDEX = -1
    }
}