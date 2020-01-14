package com.heickjack.sampletestapp.ui

import com.heickjack.sampletestapp.R
import com.heickjack.sampletestapp.databinding.ListItemMerchantBinding
import com.heickjack.sampletestapp.model.Merchant
import com.xwray.groupie.databinding.BindableItem

class ListItemMerchant(var merchant:Merchant) : BindableItem<ListItemMerchantBinding>() {

    private lateinit var mBinding:ListItemMerchantBinding

    override fun getLayout(): Int {
        return R.layout.list_item_merchant
    }

    override fun bind(viewBinding: ListItemMerchantBinding, position: Int) {
        mBinding = viewBinding

        mBinding.textId.text = "ID: ${merchant.id}"
        mBinding.textName.text = merchant.list_name
        mBinding.textDistance.text = String.format(mBinding.root.context.getString(R.string.text_km_away), merchant.distance)
    }
}