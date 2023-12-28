package com.example.coin.presentation.SpecificCryptocurrencyScreen

import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.absoluteValue

fun roundNumber(value: Double): Double {
    val absValue = value.absoluteValue

    val decimalPlaces = when {
        absValue >= 1000 -> 2
        absValue >= 1 -> 3
        else -> 6
    }
    return BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_UP).toDouble()
}
