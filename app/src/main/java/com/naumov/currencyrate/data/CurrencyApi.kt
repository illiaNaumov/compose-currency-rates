package com.naumov.currencyrate.data

import com.naumov.currencyrate.model.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CurrencyApi {

    @GET("historical/{year}.json")
    suspend fun getCurrencies(
        @Path("year") year: String,
        @Query("app_id") appId: String,
        @Query("symbols") currencies: String,
    ): CurrencyResponse
}