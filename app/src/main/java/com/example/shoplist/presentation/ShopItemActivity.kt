package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem

class ShopItemActivity : AppCompatActivity() {

    private var mode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_INDEX

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
//        Запускаем  фрагмент только 1 раз при первом вызове активити
        if(savedInstanceState == null){
            launchRightMode()
        }
    }

    private fun launchRightMode() {
        val fragment = when (mode) {
            EDIT_MODE ->
                ShopItemFragment.getEditItemFragmentInstance(shopItemId)
            ADD_MODE ->
                ShopItemFragment.getAddItemFragmentInstance()
            else -> throw RuntimeException("Unknown mode $mode")
        }

//        Пересоздаем фрагмент с помощью replace а не add, чтобы не плодить фрагменты
        supportFragmentManager.beginTransaction().replace(R.id.shop_item_container, fragment).commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE_EXTRA)) {
            throw RuntimeException("Param screen mode not found")
        }

        mode = intent.getStringExtra(SCREEN_MODE_EXTRA) ?: MODE_UNKNOWN

        if (mode == EDIT_MODE) {
            shopItemId = intent.getIntExtra(SHOP_ITEM_ID_EXTRA, ShopItem.UNDEFINED_INDEX)
        }
    }


    companion object {
        private const val SHOP_ITEM_ID_EXTRA = "shop_item_id_extra"
        private const val SCREEN_MODE_EXTRA = "screen_mode_extra"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val MODE_UNKNOWN = "unknown_mode"

        fun getAddItemIntent(
            context: Context
        ): Intent {
            return Intent(context, ShopItemActivity::class.java).apply {
                putExtra(SCREEN_MODE_EXTRA, ADD_MODE)
            }
        }

        fun getEditItemIntent(
            context: Context,
            shopItemId: Int
        ): Intent {
            val intent = Intent(context, ShopItemActivity::class.java).apply {
                putExtra(SHOP_ITEM_ID_EXTRA, shopItemId)
                putExtra(SCREEN_MODE_EXTRA, EDIT_MODE)
            }
            return intent
        }
    }
}