package com.cranaya.data.requestmanager.mapper

import com.cranaya.data.requestmanager.dto.ProductDto
import com.cranaya.domain.model.Product
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Mapper class to transform a [ProductDto] instance into a [Product] instance
 *
 * @author Cristian Anaya
 */
@Singleton
class ProductMapper @Inject constructor() {

    /**
     * Maps the given instance of [ProductDto] to an instance of [Product].
     *
     * @param dto the object to be mapped
     * @return the mapped instance or null if the input is null
     */
    fun map(dto: ProductDto?): Product? =
        dto?.takeIf(this::getMandatoryFieldsFilter)?.run {
            Product(
                id = id!!,
                title = title!!,
                price = price!!,
                currencyId = currencyId!!,
                availableQuantity = availableQuantity,
                soldQuantity = soldQuantity,
                condition = mapCondition(condition),
                thumbnail = thumbnail,
                installments = mapInstallments(installments),
                address = mapAddress(address),
                shipping = mapShipping(shipping),
                attributes = mapAttributes(attributes),
                originalPrice = originalPrice,
                seller = mapSeller(seller),
                pictures = mapPictures(pictures)
            )
        }

    private fun getMandatoryFieldsFilter(dto: ProductDto) =
        listOf(dto.id, dto.title, dto.currencyId).all { !it.isNullOrBlank() }
                && dto.price != null

    private fun mapCondition(condition: String?): Product.Condition? =
        Product.Condition.values().find { it.value == condition }

    private fun mapInstallments(installments: ProductDto.Installments?): Product.Installments? =
        installments
            ?.takeIf { it ->
                listOf(it.quantity, it.amount).all { it != null } && !it.currencyId.isNullOrBlank()
            }
            ?.run {
                Product.Installments(
                    quantity = quantity!!,
                    amount = amount!!,
                    currencyId = currencyId!!
                )
            }

    private fun mapSeller(seller: ProductDto.Seller?): Product.Seller? =
        seller
            ?.takeIf { it ->
                listOf(it.id, it.nickName).all { it != null }

            }
            ?.run {
                Product.Seller(
                    id = id!!,
                    nickName = nickName!!
                )
            }

    private fun mapAddress(address: ProductDto.Address?): String? {
        val result = listOf(address?.stateName, address?.cityName)
            .filter { !it.isNullOrBlank() }
            .joinToString(", ")

        return result.ifEmpty { null }
    }

    private fun mapShipping(shipping: ProductDto.Shipping?): Product.Shipping =
        Product.Shipping(
            freeShipping = shipping?.freeShipping ?: false,
            storePickUp = shipping?.storePickUp ?: false
        )

    private fun mapAttributes(attributes: List<ProductDto.Attribute?>?): List<Product.Attribute> =
        attributes
            ?.filter { !it?.name.isNullOrBlank() && !it?.valueName.isNullOrBlank() }
            ?.map {
                Product.Attribute(
                    name = it!!.name!!,
                    valueName = it.valueName!!
                )
            } ?: emptyList()

    private fun mapPictures(pictures: List<ProductDto.Picture?>?): List<Product.Picture> =
        pictures
            ?.filter { !it?.id.isNullOrBlank() && !it?.secureUrl.isNullOrBlank() }
            ?.map {
                Product.Picture(
                    id = it!!.id!!,
                    secureUrl = it.secureUrl!!
                )
            } ?: emptyList()
}
