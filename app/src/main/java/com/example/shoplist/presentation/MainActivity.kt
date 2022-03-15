package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var llShopList: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        llShopList = findViewById(R.id.llShopList)
        mainViewModel.shopListLiveData.observe(this) {
            showList(it)
        }
    }

    fun showList(shopList: List<ShopItem>) {

        llShopList.removeAllViews()
        for (shopItem in shopList) {

            val itemLayoutResource = if (shopItem.enabled) {
                R.layout.item_shop_enabled
            } else {
                R.layout.item_shop_disabled
            }
            val itemView: View =
                layoutInflater.inflate(itemLayoutResource, llShopList, false)
            val tvName = itemView.findViewById<TextView>(R.id.tv_name)
            val tvCount = itemView.findViewById<TextView>(R.id.tv_count)
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()

            itemView.setOnLongClickListener {
                mainViewModel.changeEnableState(shopItem)
                true
            }

            llShopList.addView(itemView)
        }
    }
}