package com.cranaya.mercadolibrechallenge.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import com.bumptech.glide.Glide
import com.cranaya.domain.model.Product.Picture
import com.cranaya.mercadolibrechallenge.R
import com.cranaya.mercadolibrechallenge.core.base.BaseViewPagerAdapter
import com.cranaya.mercadolibrechallenge.databinding.ItemImageBinding

/**
 * [BaseViewPagerAdapter] implementation to display a list of image with slider
 *
 * @author Cristian Anaya
 */
class ImagesSliderAdapter(
    private val context: Context,
    private val pictureList: List<Picture>
) : BaseViewPagerAdapter<Picture, ItemImageBinding>(pictureList) {

    override fun setupView(container: ViewGroup, position: Int): View {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView = inflater.inflate(R.layout.item_image, container, false)
        val image = itemView.findViewById(R.id.image) as ImageView

        Glide.with(itemView.context)
            .load(pictureList[position].secureUrl)
            .into(image)
        container.addView(itemView)
        return itemView
    }

}