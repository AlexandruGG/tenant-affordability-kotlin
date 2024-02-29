package com.alexgg.infrastructure

import java.math.BigDecimal

fun String.extractElements(): List<String> {
    val values = mutableListOf<String>()
    var currentValue = StringBuilder()
    var withinQuotes = false

    for (char in this) {
        when {
            char == '"' -> withinQuotes = !withinQuotes
            char == ',' && !withinQuotes -> {
                values.add(currentValue.toString())
                currentValue = StringBuilder()
            }

            else -> currentValue.append(char)
        }
    }
    values.add(currentValue.toString())

    return values
}

fun String.parseMonetaryAmount(): BigDecimal = replace(Regex("[^\\d.]"), "").toBigDecimalOrNull() ?: BigDecimal.ZERO
