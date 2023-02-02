package com.cranaya.mercadolibrechallenge.presentation.adapters

import com.cranaya.domain.model.ProductSearchHistoryModel
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseViewHolderAdapter
import com.cranaya.mercadolibrechallenge.databinding.ItemHistoryBinding

/**
 * [BaseViewHolderAdapter] implementation to display a list of product history
 *
 * @author Cristian Anaya
 */
class ProductSearchHistoryAdapter(
    private val onArrowClicked: (ProductSearchHistoryModel) -> Unit,
    private val onHistoryClicked: (ProductSearchHistoryModel) -> Unit
) : BaseViewHolderAdapter<ProductSearchHistoryModel, ItemHistoryBinding>() {

    override fun layoutId() = R.layout.item_history

    override fun onBind(item: ProductSearchHistoryModel) {
        with(binding) {
            textViewQuery.text = item.title
            imageArrow.setOnClickListener {
                onArrowClicked(item)
            }
            container.setOnClickListener {
                onHistoryClicked(item)
            }
        }
    }

}