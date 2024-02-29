package com.alexgg.domain

import com.alexgg.infrastructure.CsvFileLoader
import com.alexgg.infrastructure.extractElements
import com.alexgg.infrastructure.parseMonetaryAmount
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.math.BigDecimal

class PropertyLoader : CsvFileLoader<Property> {
    companion object {
        private const val HEADER_LINES = 1
    }

    override fun load(path: String): List<Property> {
        val lines = getReader(path).readLines().drop(HEADER_LINES)

        return lines.map {
            val elements = it.extractElements()

            Property(
                id = elements[0].toInt(),
                address = elements[1],
                price = elements[2].parseMonetaryAmount(),
            )
        }
    }

    private fun getReader(path: String): BufferedReader =
        javaClass.getResourceAsStream(path)?.bufferedReader() ?: throw FileNotFoundException("Could not find properties file: '$path'")
}

data class Property(
    val id: Int,
    val address: String,
    val price: BigDecimal,
)
