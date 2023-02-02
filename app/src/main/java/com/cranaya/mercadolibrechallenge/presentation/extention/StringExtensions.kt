package com.cranaya.mercadolibrechallenge.presentation.extention

const val PRODUCT_THUMBNAIL_REPLACE_COUNT_OF_DIGITS = 3
const val PRODUCT_THUMBNAIL_REPLACE_TO_PREFIX = "https"


/**
 * [String]To be able to load an image using a library it is necessary to replace the prefix http over https
 *
 * @return a String with the url https
 */
fun String.setParseUrl(): String =
    this.replaceRange(0..PRODUCT_THUMBNAIL_REPLACE_COUNT_OF_DIGITS, PRODUCT_THUMBNAIL_REPLACE_TO_PREFIX)

