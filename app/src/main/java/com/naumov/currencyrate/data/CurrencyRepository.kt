package com.naumov.currencyrate.data

import com.naumov.currencyrate.APP_ID
import com.naumov.currencyrate.formatToServerTime
import com.naumov.currencyrate.model.CurrencyRate
import com.naumov.currencyrate.model.MonthlyRates
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class CurrencyRepository(
    private val currencyApi: CurrencyApi
) {
    suspend fun getCurrencyList(dates: List<Long>): List<MonthlyRates> = coroutineScope {
        dates.map { day ->
            async { getCurrencyForDay(day, currencies) }
        }
            .awaitAll()
            .sortedBy { it.timestamp }
    }

    private suspend fun getCurrencyForDay(timeStamp: Long, currencies: String): MonthlyRates {
        check(APP_ID.isNotBlank()) { "Please set your app id in Constant" }

        val time = formatToServerTime(timeStamp)
        val rates = currencyApi.getCurrencies(time, APP_ID, currencies).rates.entries
            .sortedBy { if (it.key == EUR) 0 else 1 }
            .map { CurrencyRate.toCurrencyRate(it.key, it.value) }

        return MonthlyRates(timeStamp, rates)
    }

    private companion object {
        const val EUR = "EUR"
        const val currencies = "EUR,SEK,UAH,CAD,CZK,CNH,CHF,NOK,ISK,TRY"
    }
}