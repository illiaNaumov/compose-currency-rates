package com.naumov.currencyrate

import com.naumov.currencyrate.data.CurrencyApi
import com.naumov.currencyrate.data.CurrencyRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RepositoryProvider {

    val repository = CurrencyRepository(createApi())

    private fun createApi(): CurrencyApi {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        return retrofit.create(CurrencyApi::class.java)
    }
}