package com.naumov.currencyrate.model

import java.math.RoundingMode

data class CurrencyRate(
    val currencyCode: String,
    val rate: Double,
) {
    companion object {
        fun toCurrencyRate(currencyCode: String, rate: Double): CurrencyRate {
            return CurrencyRate(
                currencyCode,
                rate.toBigDecimal().setScale(2, RoundingMode.HALF_UP).toDouble()
            )
        }
    }
}