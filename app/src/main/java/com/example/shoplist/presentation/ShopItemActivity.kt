package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.google.android.material.textfield.TextInputEditText

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopItemViewModel: ShopItemViewModel
    private lateinit var tietName: TextInputEditText
    private lateinit var tietCount: TextInputEditText
    private lateinit var btnSave: Button
    private var mode: String = ADD_MODE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        tietName = findViewById(R.id.tietName)
        tietCount = findViewById(R.id.tietCount)

        mode = intent.getStringExtra(SCREEN_MODE_EXTRA) ?: ADD_MODE

        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        shopItemViewModel.shopItem.observe(this) {
            if (mode == EDIT_MODE) {
                tietName.text = Editable.Factory.getInstance().newEditable(it.name)
                tietCount.text = Editable.Factory.getInstance().newEditable(it.count.toString())
            }
        }

        shopItemViewModel.errorInputName.observe(this) {
            if (it) {
                Toast.makeText(applicationContext, "Имя введено неверно", Toast.LENGTH_SHORT).show()
            }
        }
        shopItemViewModel.errorInputCount.observe(this) {
            if (it) {
                Toast.makeText(applicationContext, "Количество введено неверно", Toast.LENGTH_SHORT)
                    .show()
            }
        }
        shopItemViewModel.shouldClose.observe(this) {
            finish()
        }

        initSaveBtn()
    }

    private fun initSaveBtn() {
        btnSave = findViewById(R.id.btnEditSave)
        btnSave.setOnClickListener {
            when (mode) {
                ADD_MODE -> shopItemViewModel.addShopItem(
                    tietName.text.toString(),
                    tietCount.text.toString()
                )
                EDIT_MODE -> shopItemViewModel.updateShopItem(
                    tietName.text.toString(),
                    tietCount.text.toString()
                )
            }
        }
    }

    companion object {
        private const val SHOP_ITEM_ID_EXTRA = "shop_item_id_extra"
        private const val SCREEN_MODE_EXTRA = "screen_mode_extra"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"

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