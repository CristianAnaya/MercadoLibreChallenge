package com.cranaya.mercadolibrechallenge.presentation.adapters

import android.util.Log
import com.bumptech.glide.Glide
import com.cranaya.domain.model.Product
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseViewHolderAdapter
import com.cranaya.mercadolibrechallenge.databinding.ItemProductBinding
import com.cranaya.mercadolibrechallenge.presentation.extention.formatNoDecimals
import com.cranaya.mercadolibrechallenge.presentation.extention.setParseUrl
import com.cranaya.mercadolibrechallenge.presentation.extention.setTextOrHide
import javax.inject.Inject

/**
 * [ProductListAdapter] implementation to display a list of products
 *
 * @author Cristian Anaya
 */
class ProductListAdapter @Inject constructor(
    var onProductClicked: (String) -> Unit,
) : BaseViewHolderAdapter<Product, ItemProductBinding>() {

    override fun layoutId() = R.layout.item_product

    override fun onBind(item: Product) {
        with(binding) {
            Glide.with(root.context)
                .load(item.thumbnail?.setParseUrl())
                .into(productImage)
            container.setOnClickListener {
                onProductClicked(item.id)
            }
            productName.text = item.title

            price.text = root.context.getString(R.string.price, "$", item.price.toInt())
            itemListInstallments.setTextOrHide(
                root.context.getString(
                    R.string.product_installments,
                    item.installments?.quantity,
                    item.installments?.amount?.formatNoDecimals()
                )
            )
            itemListFreeShipping.setTextOrHide(item.shipping.freeShipping)
        }
    }

}