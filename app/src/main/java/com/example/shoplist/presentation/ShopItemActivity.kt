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
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText
import java.lang.RuntimeException

class ShopItemActivity : AppCompatActivity() {

    private lateinit var shopItemViewModel: ShopItemViewModel
    private lateinit var tietName: TextInputEditText
    private lateinit var tietCount: TextInputEditText
    private lateinit var btnSave: Button
    private var mode: String = MODE_UNKNOWN
    private lateinit var onSaveCallback: ((name: String, count: String) -> Unit)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        initViews()
        parseIntent()
        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        launchRightMode()
        observeViewModel()
    }

    private fun launchRightMode() {
        mode = intent.getStringExtra(SCREEN_MODE_EXTRA) ?: MODE_UNKNOWN
        when (mode) {
            EDIT_MODE ->
                launchEditMode()
            ADD_MODE ->
                launchAddMode()
        }
    }

    private fun observeViewModel() {
        shopItemViewModel.shopItem.observe(this) {
            tietName.text = Editable.Factory.getInstance().newEditable(it.name)
            tietCount.text = Editable.Factory.getInstance().newEditable(it.count.toString())
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
    }

    private fun launchEditMode() {
        val shopItemId: Int = intent.getIntExtra(SHOP_ITEM_ID_EXTRA, ShopItem.UNDEFINED_INDEX)
        shopItemViewModel.getShopItemById(shopItemId)
        onSaveCallback =
            { name: String, count: String -> shopItemViewModel.updateShopItem(name, count) }
    }

    private fun launchAddMode() {
        onSaveCallback =
            { name: String, count: String -> shopItemViewModel.addShopItem(name, count) }
    }

    private fun initViews() {
        tietName = findViewById(R.id.tietName)
        tietCount = findViewById(R.id.tietCount)
        initSaveBtn()
    }

    private fun initSaveBtn() {
        btnSave = findViewById(R.id.btnEditSave)
        btnSave.setOnClickListener {
            onSaveCallback.invoke(tietName.text.toString(), tietCount.text.toString())
        }
    }

    private fun parseIntent() {
        if (!intent.hasExtra(SCREEN_MODE_EXTRA)) {
            throw RuntimeException("Param screen mode not found")
        }
        val mode = intent.getStringExtra(SCREEN_MODE_EXTRA)

        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown mode $mode")
        }

        if (mode == EDIT_MODE) {
            if (!intent.hasExtra(SHOP_ITEM_ID_EXTRA)) {
                throw RuntimeException("Param shop item id not found")
            }
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