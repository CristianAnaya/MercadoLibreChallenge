package com.cranaya.mercadolibrechallenge.presentation.extention

import android.view.View
import android.widget.TextView

/**
 * [TextView] extension to set the given text. If the text is false the view is hidden
 */
fun TextView.setTextOrHide(content: Boolean?) {
    if (content!!) {
        visibility = View.VISIBLE
        text = "Envío gratis"
    } else {
        View.GONE
    }
}

/**
 * [TextView] extension to set the given text. If the text is null the view is hidden
 *
 * @param content the text to set
 */
fun TextView.setTextOrHide(content: String?) {
    content?.apply {
        visibility = View.VISIBLE
        text = content
    } ?: run { visibility = View.GONE }
}
