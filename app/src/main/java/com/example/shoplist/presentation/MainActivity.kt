package com.example.shoplist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.example.shoplist.presentation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.shopListLiveData.observe(this) {
            shopListAdapter.shopList = it
        }
    }

    fun initRecyclerView() {
        val recyclerView: RecyclerView = findViewById(R.id.rv_shop_list)
        shopListAdapter = ShopListAdapter()
        recyclerView.adapter = shopListAdapter
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.ENABLED_VIEW_TYPE,
            ShopListAdapter.MAX_POOL_SIZE
        )
        recyclerView.recycledViewPool.setMaxRecycledViews(
            ShopListAdapter.DISABLED_VIEW_TYPE,
            ShopListAdapter.MAX_POOL_SIZE
        )

        shopListAdapter.onShopItemLongClickListener = {
            mainViewModel.changeEnableState(shopItem = it)
        }
        shopListAdapter.onShopItemClickListener = {
            val shopItemInfo: ShopItem = mainViewModel.getShopItemById(it.id)
            Log.d("SHOP_ITEM_INFO", shopItemInfo.toString())

        }
    }
}