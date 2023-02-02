package com.cranaya.domain.factory

import com.cranaya.domain.model.Product
import com.cranaya.domain.model.Product.Installments
import com.cranaya.domain.model.Product.Shipping
import com.cranaya.domain.model.Product.Attribute
import com.cranaya.domain.model.Product.Seller
import com.cranaya.domain.model.Product.Picture

/**
 * class used to generate objects
 *
 * @author Cristian Anaya
 */
object ProductFactory {
    fun generateProductList(size: Int): List<Product> {
        val movieList = mutableListOf<Product>()
        repeat(size) {
            movieList.add(generateProduct())
        }
        return movieList
    }

    fun generateAttributeList(size: Int): List<Attribute> {
        val movieList = mutableListOf<Attribute>()
        repeat(size) {
            movieList.add(generateAttribute())
        }
        return movieList
    }

    fun generatePictureList(size: Int): List<Picture> {
        val movieList = mutableListOf<Picture>()
        repeat(size) {
            movieList.add(generatePicture())
        }
        return movieList
    }

    fun generateProduct(): Product {
        return Product(
            id = DataFactory.getRandomString(),
            title = DataFactory.getRandomString(),
            price = DataFactory.getRandomFloat(),
            currencyId = DataFactory.getRandomString(),
            availableQuantity = DataFactory.getRandomInt(),
            soldQuantity = DataFactory.getRandomInt(),
            thumbnail = DataFactory.getRandomString(),
            address = DataFactory.getRandomString(),
            shipping = generateShipping(),
            attributes = generateAttributeList(2),
            seller = generateSeller(),
            originalPrice = DataFactory.getRandomFloat(),
            pictures = generatePictureList(2),
            condition = Product.Condition.NEW,
            installments = generateInstallments()
        )
    }

    fun generatePicture(): Picture {
        return Picture(
            id = DataFactory.getRandomString(),
            secureUrl = DataFactory.getRandomString()
        )
    }

    fun generateInstallments(): Installments {
        return Installments(
            quantity = DataFactory.getRandomInt(),
            amount = DataFactory.getRandomFloat(),
            currencyId = DataFactory.getRandomString()
        )
    }

    fun generateSeller(): Seller {
        return Seller(
            id = DataFactory.getRandomString(),
            nickName = DataFactory.getRandomString(),
        )
    }

    fun generateShipping(): Shipping {
        return Shipping(
            freeShipping = DataFactory.getRandomBoolean(),
            storePickUp = DataFactory.getRandomBoolean(),
        )
    }

    fun generateAttribute(): Attribute {
        return Attribute(
            name = DataFactory.getRandomString(),
            valueName = DataFactory.getRandomString()
        )
    }

}