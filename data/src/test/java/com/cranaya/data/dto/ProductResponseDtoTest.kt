package com.cranaya.data.dto

import com.cranaya.data.utils.TestUtils.readFromFile
import com.cranaya.data.utils.TestUtils.readFromJson
import com.cranaya.data.requestmanager.dto.ProductResponseDto
import org.junit.Assert
import org.junit.Test

/**
 * Unit test for Product DTO classes
 *
 * @author Cristian Anaya
 */
class ProductResponseDtoTest {
    @Test
    fun test_Deserialize() {
        val resultDto = readFromFile(
            path = "/products.json",
            clazz = ProductResponseDto::class.java
        )


        Assert.assertEquals(1, resultDto.results!!.size)

        resultDto.results!![0]!!.apply {
            Assert.assertEquals("MCO481185321", id)
            Assert.assertEquals("Motorola Moto G6 Octacore 32gb 3ram Huella 4g Dualcam 2018", title)
            Assert.assertEquals(599900f, price)
            Assert.assertEquals("COP", currencyId)
            Assert.assertEquals(1, availableQuantity)
            Assert.assertEquals(0, soldQuantity)
            Assert.assertEquals("used", condition)
            Assert.assertEquals(
                "http://mco-s2-p.mlstatic.com/736765-MCO31544518191_072019-I.jpg",
                thumbnail
            )
            Assert.assertEquals(36, installments!!.quantity)
            Assert.assertEquals(16663.89f, installments!!.amount)
            Assert.assertEquals("COP", installments!!.currencyId)
            Assert.assertEquals("Bogotá D.C.", address!!.stateName)
            Assert.assertEquals("Usaquén", address!!.cityName)
            Assert.assertTrue(shipping!!.freeShipping!!)
            Assert.assertFalse(shipping!!.storePickUp!!)

            Assert.assertEquals(5, attributes!!.size)
            Assert.assertEquals("Marca", attributes!![0]!!.name)
            Assert.assertEquals("Motorola", attributes!![0]!!.valueName)
            Assert.assertEquals("Condición del ítem", attributes!![1]!!.name)
            Assert.assertEquals("Usado", attributes!![1]!!.valueName)
            Assert.assertEquals("Línea", attributes!![2]!!.name)
            Assert.assertEquals("Moto", attributes!![2]!!.valueName)
            Assert.assertEquals("Modelo", attributes!![3]!!.name)
            Assert.assertEquals("G6 Dual SIM", attributes!![3]!!.valueName)
            Assert.assertEquals("Modelo del procesador", attributes!![4]!!.name)
            Assert.assertEquals("Snapdragon 450", attributes!![4]!!.valueName)

            Assert.assertEquals(900000f, originalPrice)
        }
    }

    @Test
    fun test_Deserialize_MissingProductFields() {
        val resultDto = readFromJson(
            json = "{" +
                    "\"results\": [{}]" +
                    "}",
            clazz = ProductResponseDto::class.java
        )
        Assert.assertEquals(1, resultDto.results!!.size)

        resultDto.results!![0]!!.apply {
            Assert.assertNull(id)
            Assert.assertNull(title)
            Assert.assertNull(price)
            Assert.assertNull(currencyId)
            Assert.assertNull(availableQuantity)
            Assert.assertNull(soldQuantity)
            Assert.assertNull(condition)
            Assert.assertNull(thumbnail)
            Assert.assertNull(installments)
            Assert.assertNull(address)
            Assert.assertNull(shipping)
            Assert.assertNull(attributes)
            Assert.assertNull(originalPrice)
        }
    }
}