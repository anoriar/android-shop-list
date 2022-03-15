package com.example.shoplist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import java.lang.RuntimeException

class ShopListAdapter : RecyclerView.Adapter<ShopListAdapter.ShopItemViewHolder>() {

    var viewHoldersCount = 0

    var shopList = listOf<ShopItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    companion object {
        const val ENABLED_LAYOUT_RESOURCE = R.layout.item_shop_enabled
        const val DISABLED_LAYOUT_RESOURCE = R.layout.item_shop_disabled

        const val ENABLED_VIEW_TYPE = 1
        const val DISABLED_VIEW_TYPE = 0

        //        Количество пулов RecyclerView
        const val MAX_POOL_SIZE = 15
    }

    class ShopItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tv_name)
        val tvCount: TextView = view.findViewById(R.id.tv_count)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {

        Log.d("VIEW_HOLDER_COUNT", "view holders count: ${++viewHoldersCount}")
        val layoutResource = when (viewType) {
            ENABLED_VIEW_TYPE -> ENABLED_LAYOUT_RESOURCE
            DISABLED_VIEW_TYPE -> DISABLED_LAYOUT_RESOURCE
            else -> throw RuntimeException("View type not found: $viewType")
        }
        val view =
            LayoutInflater.from(parent.context)
                .inflate(layoutResource, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        val shopItem: ShopItem = shopList[position]

        holder.tvName.text = shopItem.name
        holder.tvCount.text = shopItem.count.toString()
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem: ShopItem = shopList[position]
        return when (shopItem.enabled) {
            true -> ENABLED_VIEW_TYPE
            else -> DISABLED_VIEW_TYPE
        }
    }

    override fun getItemCount(): Int = shopList.size
}