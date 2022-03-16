package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputEditText

class ShopItemActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var tietName: TextInputEditText
    private lateinit var tietCount: TextInputEditText
    private lateinit var btnSave: Button
    private var shopItem: ShopItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)

        tietName = findViewById(R.id.tietName)
        tietCount = findViewById(R.id.tietCount)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)


        if (intent.hasExtra(SHOP_ITEM_ID_EXTRA)) {
            val shopItem = mainViewModel.getShopItemById(intent.getIntExtra(SHOP_ITEM_ID_EXTRA, -1))
            tietName.text = Editable.Factory.getInstance().newEditable(shopItem.name)
            tietCount.text = Editable.Factory.getInstance().newEditable(shopItem.count.toString())
        }

        initSaveBtn()
    }

    private fun initSaveBtn() {
        btnSave = findViewById(R.id.btnEditSave)
        btnSave.setOnClickListener {
            addUpdateShopItem()
        }
    }

    private fun addUpdateShopItem() {

        val name: String = tietName.text.toString()
        val count: Int = tietName.text.toString().toInt()
        if (shopItem == null) {
            mainViewModel.addShopItem(ShopItem(name, count, true))
        } else {
            shopItem?.let {
                it.name = name
                it.count = count
                mainViewModel.updateShopItem(it)
            }
        }

    }

    companion object {
        private const val SHOP_ITEM_ID_EXTRA = "SHOP_ITEM_ID_EXTRA"
        fun getIntent(
            context: Context,
            shopItemId: Int? = null
        ): Intent {
            val intent = Intent(context, ShopItemActivity::class.java).apply {
                putExtra(SHOP_ITEM_ID_EXTRA, shopItemId)
            }
            return intent
        }
    }
}