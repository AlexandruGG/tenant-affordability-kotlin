package com.alexgg.domain

import com.alexgg.infrastructure.CsvFileLoader
import com.alexgg.infrastructure.extractElements
import com.alexgg.infrastructure.parseMonetaryAmount
import java.io.BufferedReader
import java.io.FileNotFoundException
import java.math.BigDecimal
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class StatementLoader : CsvFileLoader<Statement> {
    companion object {
        private const val HEADER_LINES = 11
    }

    override fun load(path: String): List<Statement> {
        val lines = getReader(path).readLines().drop(HEADER_LINES)

        return lines.map {
            val elements = it.extractElements()

            Statement(
                date = LocalDate.parse(elements[0], DateTimeFormatter.ofPattern("d['st']['nd']['rd']['th'] MMMM yyyy")),
                paymentType = PaymentType.fromValue(elements[1]),
                details = elements[2],
                moneyOut = elements[3].parseMonetaryAmount(),
                moneyIn = elements[4].parseMonetaryAmount(),
                balance = elements[5].parseMonetaryAmount(),
            )
        }
    }

    private fun getReader(path: String): BufferedReader =
        javaClass.getResourceAsStream(path)?.bufferedReader()
            ?: throw FileNotFoundException("Could not find bank statements file: '$path'")
}

data class Statement(
    val date: LocalDate,
    val paymentType: PaymentType,
    val details: String,
    val moneyOut: BigDecimal,
    val moneyIn: BigDecimal,
    val balance: BigDecimal,
)

enum class PaymentType(
    val value: String,
) {
    ATM("ATM"),
    DIRECT_DEBIT("Direct Debit"),
    CARD_PAYMENT("Card Payment"),
    BANK_CREDIT("Bank Credit"),
    STANDING_ORDER("Standing Order"),
    BANK_TRANSFER("Bank Transfer"),
    ;

    companion object {
        fun fromValue(value: String): PaymentType =
            entries.find { it.value == value } ?: throw IllegalArgumentException("Invalid payment type: '$value'")
    }
}
