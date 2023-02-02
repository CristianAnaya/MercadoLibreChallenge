package com.cranaya.mercadolibrechallenge.core.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseViewHolderAdapter<T, B: ViewDataBinding> : RecyclerView.Adapter<BaseViewHolderAdapter<T, B>.ViewHolder>() {
    private var list: ArrayList<T> = arrayListOf()
    lateinit var binding: B

    @LayoutRes
    abstract fun layoutId(): Int

    abstract fun onBind(item: T)
    
    fun submitList(newList: List<T>){
        val diffResult = DiffUtil.calculateDiff(BaseDiffCallback(list, newList))
        diffResult.dispatchUpdatesTo(this)
        list.clear()
        list.addAll(newList)
    }

    private fun getItem(position: Int) = list[position]

    override fun getItemViewType(position: Int) = list[position].hashCode()

    override fun getItemId(position: Int) = position.toLong()

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(holder.adapterPosition))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = DataBindingUtil.inflate(layoutInflater, layoutId(), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(binding: B) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: T){
            onBind(item)
        }
    }

}