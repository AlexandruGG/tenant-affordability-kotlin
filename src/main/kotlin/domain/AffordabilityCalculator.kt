package com.alexgg.domain

import java.math.BigDecimal
import java.time.Month

class AffordabilityCalculator {
    companion object {
        private val AFFORDABILITY_RATIO: BigDecimal = BigDecimal.valueOf(1.25)
    }

    fun compute(
        statements: List<Statement>,
        properties: List<Property>,
    ): List<Property> {
        val statementsByMonth = statements.groupBy { it.date.month }

        val excessIncome =
            computeExcessIncome(statementsByMonth.getRecurringIncomeByMonth(), statementsByMonth.getRecurringExpensesByMonth())

        return properties.filter { it.price.multiply(AFFORDABILITY_RATIO) < excessIncome }
    }

    private fun computeExcessIncome(
        incomeByMonth: Map<Month, BigDecimal>,
        expensesByMonth: Map<Month, BigDecimal>,
    ): BigDecimal {
        val averageIncome = incomeByMonth.values.reduce { acc, it -> acc.add(it) }.divide(incomeByMonth.size.toBigDecimal())
        val averageExpenses = expensesByMonth.values.reduce { acc, it -> acc.add(it) }.divide(expensesByMonth.size.toBigDecimal())

        return averageIncome.subtract(averageExpenses)
    }
}

private fun Map<Month, List<Statement>>.getRecurringIncomeByMonth(): Map<Month, BigDecimal> {
    return mapValues {
        it.value.filter { st -> st.paymentType == PaymentType.BANK_CREDIT }
            .fold(BigDecimal.ZERO) { acc, st -> acc.add(st.moneyIn) }
    }
}

private fun Map<Month, List<Statement>>.getRecurringExpensesByMonth(): Map<Month, BigDecimal> {
    return mapValues {
        it.value.filter { st -> st.paymentType == PaymentType.DIRECT_DEBIT }
            .fold(BigDecimal.ZERO) { acc, st -> acc.add(st.moneyOut) }
    }
}
