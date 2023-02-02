package com.cranaya.domain.model

/**
 * Model classes for a product
 *
 * @author Cristian Anaya
 */
data class ProductSearchHistoryModel constructor(
    val title: String,
    var timestamp: Long = 0L
)