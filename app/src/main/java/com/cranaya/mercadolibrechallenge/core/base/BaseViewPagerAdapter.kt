package com.cranaya.mercadolibrechallenge.core.base

import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.ViewDataBinding
import androidx.viewpager.widget.PagerAdapter


abstract class BaseViewPagerAdapter<T, B: ViewDataBinding>(
    private val list: List<T>
) : PagerAdapter() {

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return setupView(container, position)
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }

    abstract fun setupView(container: ViewGroup, position: Int): View

}