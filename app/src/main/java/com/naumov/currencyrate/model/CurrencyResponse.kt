package com.naumov.currencyrate.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class CurrencyResponse(
    @Json(name = "rates") val rates: Map<String, Double>
)