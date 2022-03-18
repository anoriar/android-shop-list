package com.example.shoplist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.textfield.TextInputLayout

class ShopItemFragment : Fragment() {

    private lateinit var shopItemViewModel: ShopItemViewModel
    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var tietName: EditText
    private lateinit var tietCount: EditText
    private lateinit var btnSave: Button
    private lateinit var onSaveCallback: ((name: String, count: String) -> Unit)
    private var mode: String = MODE_UNKNOWN
    private var shopItemId: Int = ShopItem.UNDEFINED_INDEX

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(view)

        shopItemViewModel = ViewModelProvider(this).get(ShopItemViewModel::class.java)
        launchRightMode()
        addTextChangeListeners()
        observeViewModel()
    }

    private fun launchRightMode() {
        when (mode) {
            EDIT_MODE ->
                launchEditMode()
            ADD_MODE ->
                launchAddMode()
        }
    }

    private fun observeViewModel() {
        shopItemViewModel.shopItem.observe(viewLifecycleOwner) {
            tietName.text = Editable.Factory.getInstance().newEditable(it.name)
            tietCount.text = Editable.Factory.getInstance().newEditable(it.count.toString())
        }

        shopItemViewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_name)
            } else {
                null
            }
            tilName.error = message
        }
        shopItemViewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_count)
            } else {
                null
            }
            tilCount.error = message
        }
        shopItemViewModel.shouldClose.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun addTextChangeListeners() {
        tietName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
        tietCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                shopItemViewModel.resetErrorCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun launchEditMode() {
        shopItemViewModel.getShopItemById(shopItemId)
        onSaveCallback =
            { name: String, count: String -> shopItemViewModel.updateShopItem(name, count) }
    }

    private fun launchAddMode() {
        onSaveCallback =
            { name: String, count: String -> shopItemViewModel.addShopItem(name, count) }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.tilName)
        tilCount = view.findViewById(R.id.tilCount)
        tietName = view.findViewById(R.id.tietName)
        tietCount = view.findViewById(R.id.tietCount)
        btnSave = view.findViewById(R.id.btnEditSave)
        btnSave.setOnClickListener {
            onSaveCallback.invoke(tietName.text.toString(), tietCount.text.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE_ARG)) {
            throw RuntimeException("Param screen mode not found")
        }

        mode = args.getString(SCREEN_MODE_ARG) ?: MODE_UNKNOWN
        if (mode != EDIT_MODE && mode != ADD_MODE) {
            throw RuntimeException("Unknown mode $mode")
        }


        if (mode == EDIT_MODE) {
            if (!args.containsKey(SHOP_ITEM_ID_ARG)) {
                throw RuntimeException("Param shop item id not found")
            }
            shopItemId = args.getInt(SHOP_ITEM_ID_ARG)
        }
    }


    companion object {
        private const val SHOP_ITEM_ID_ARG = "shop_item_id_extra"
        private const val SCREEN_MODE_ARG = "screen_mode_extra"
        private const val EDIT_MODE = "edit_mode"
        private const val ADD_MODE = "add_mode"
        private const val MODE_UNKNOWN = "unknown_mode"

        fun getAddItemFragmentInstance(): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE_ARG, ADD_MODE)
                }
            }
        }

        fun getEditItemFragmentInstance(
            shopItemId: Int
        ): ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE_ARG, EDIT_MODE)
                    putInt(SHOP_ITEM_ID_ARG, shopItemId)
                }
            }
        }
    }
}