package com.example.shoplist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter
    private var itemTouchHelper: ItemTouchHelper? = null
    private lateinit var addButton: FloatingActionButton
    private var shopItemContainer: FragmentContainerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initRecyclerView()

        shopItemContainer = findViewById(R.id.shop_item_container)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        mainViewModel.shopListLiveData.observe(this) {
            shopListAdapter.submitList(it)
        }

        initAddButton()
    }

    fun launchFragment(fragment: ShopItemFragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.shop_item_container, fragment)
//                Добавляем в бекстек фрагмент, как активити
            .addToBackStack(null)
            .commit()
    }

    //    Режим в одну колонку? (книжная ориентация)
    fun isOnePaneMode(): Boolean {
        return shopItemContainer == null
    }

    fun initAddButton() {
        addButton = findViewById(R.id.button_add_shop_item)
        addButton.setOnClickListener {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getAddItemIntent(applicationContext)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.getAddItemFragmentInstance())
            }
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

        setupOnLongClickListener()
        setupOnClickListener()
        setupOnSwipeListener(recyclerView)
    }

    private fun setupOnSwipeListener(recyclerView: RecyclerView) {
        val callback: ItemTouchHelper.SimpleCallback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.START or ItemTouchHelper.END
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                mainViewModel.deleteShopItem(shopListAdapter.currentList[viewHolder.adapterPosition])
            }

        }
        itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper?.attachToRecyclerView(recyclerView)
    }

    private fun setupOnClickListener() {
        shopListAdapter.onShopItemClickListener = {
            if (isOnePaneMode()) {
                val intent = ShopItemActivity.getEditItemIntent(applicationContext, it.id)
                startActivity(intent)
            } else {
                launchFragment(ShopItemFragment.getEditItemFragmentInstance(it.id))
            }
        }
    }

    private fun setupOnLongClickListener() {
        shopListAdapter.onShopItemLongClickListener = {
            mainViewModel.changeEnableState(shopItem = it)
        }
    }
}