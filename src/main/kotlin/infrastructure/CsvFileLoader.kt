package com.alexgg.infrastructure

interface CsvFileLoader<T> {
    fun load(path: String): List<T>
}
