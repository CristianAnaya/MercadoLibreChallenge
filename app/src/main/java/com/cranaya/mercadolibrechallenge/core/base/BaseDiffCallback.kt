package com.cranaya.mercadolibrechallenge.core.base

import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil

/**
 * [BaseDiffCallback] implementation for [BaseViewHolderAdapter]
 *
 * @author Cristian Anaya
 */
class BaseDiffCallback<T>(
    private val listOld: List<T>,
    private val listNew: List<T>
) : DiffUtil.Callback() {

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition] == listNew[newItemPosition]
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return listOld[oldItemPosition].hashCode() == listNew[newItemPosition].hashCode()
    }

    override fun getNewListSize() = listNew.size

    override fun getOldListSize() = listOld.size

    @Nullable
    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}