package com.cranaya.data.databasemanager.factory

import java.util.UUID
import kotlin.random.Random

/**
 * Class used to generate data types to be
 * assigned to object attributes
 *
 * @author Cristian Anaya
 */

object DataFactory {
    fun getRandomString(): String {
        return UUID.randomUUID().toString().substring(0, 15)
    }

    fun getRandomLong(): Long {
        return Random.nextLong()
    }
}