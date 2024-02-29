package com.alexgg.domain

import com.alexgg.infrastructure.CsvFileLoader

class AffordabilityService(
    private val statementLoader: CsvFileLoader<Statement>,
    private val propertyLoader: CsvFileLoader<Property>,
    private val affordabilityCalculator: AffordabilityCalculator,
) {
    fun compute() =
        affordabilityCalculator.compute(
            statementLoader.load("/bank_statement.csv"),
            propertyLoader.load("/properties.csv"),
        )
}
