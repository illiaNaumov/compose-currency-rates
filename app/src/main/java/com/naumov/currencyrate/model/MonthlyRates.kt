package com.naumov.currencyrate.model

class MonthlyRates(
    val timestamp: Long,
    val currencyRates: List<CurrencyRate>
)