package com.cranaya.domain.model


/**
 * Model classes for a product
 *
 * @author Cristian Anaya
 */
data class Product(
    val id: String,
    val title: String,
    val price: Float,
    val currencyId: String,
    val availableQuantity: Int?,
    val soldQuantity: Int?,
    val condition: Condition?,
    val thumbnail: String?,
    val installments: Installments?,
    val address: String?,
    val shipping: Shipping,
    val attributes: List<Attribute>,
    val seller: Seller?,
    val originalPrice: Float?,
    val pictures: List<Picture>?
) {

    data class Installments(
        val quantity: Int,
        val amount: Float,
        val currencyId: String
    )

    data class Shipping(
        val freeShipping: Boolean,
        val storePickUp: Boolean
    )

    data class Attribute(
        val name: String,
        val valueName: String
    )

    data class Seller(
        val id: String,
        val nickName: String
    )

    data class Picture(
        val id: String,
        val secureUrl: String
    )

    enum class Condition(val value: String) {
        USED("used"),
        NEW("new")
    }
}
