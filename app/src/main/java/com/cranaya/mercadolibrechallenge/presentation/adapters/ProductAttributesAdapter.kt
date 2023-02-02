package com.cranaya.mercadolibrechallenge.presentation.adapters

import com.cranaya.domain.model.Product.Attribute
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseViewHolderAdapter
import com.cranaya.mercadolibrechallenge.databinding.ItemProductAttributeBinding

/**
 * [BaseViewHolderAdapter] implementation to display a list of the attributes of a product
 *
 * @author Cristian Anaya
 */
class ProductAttributesAdapter: BaseViewHolderAdapter<Attribute, ItemProductAttributeBinding>() {

    override fun layoutId(): Int = R.layout.item_product_attribute

    override fun onBind(item: Attribute) {
        with(binding) {
            item.apply {
                itemProductAttributeName.text = item.name
                itemProductAttributeValue.text = item.valueName
            }
        }
    }
}