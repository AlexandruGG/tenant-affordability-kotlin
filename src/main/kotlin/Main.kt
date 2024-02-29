package com.alexgg

import com.alexgg.domain.AffordabilityCalculator
import com.alexgg.domain.AffordabilityService
import com.alexgg.domain.PropertyLoader
import com.alexgg.domain.StatementLoader

fun main() {
    val service = AffordabilityService(StatementLoader(), PropertyLoader(), AffordabilityCalculator())
    with(service.compute()) {
        println("### Affordable Properties ###")
        println(joinToString("\n"))
    }
}
