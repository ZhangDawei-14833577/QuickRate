package com.example.quickrate

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CurrencyRepository {

    private val api: CurrencyApiService = Retrofit.Builder()
        .baseUrl("https://open.er-api.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(CurrencyApiService::class.java)

    suspend fun getLatestRates(baseCurrency: String): List<CurrencyRate> {
        val response = api.getLatestRates(baseCurrency)

        val targetCurrencies = when (baseCurrency) {
            "SGD" -> listOf("CNY", "USD", "AUD")
            "USD" -> listOf("SGD", "CNY", "AUD")
            "CNY" -> listOf("SGD", "USD", "AUD")
            "AUD" -> listOf("SGD", "USD", "CNY")
            else -> listOf("CNY", "USD", "AUD")
        }

        return targetCurrencies.mapNotNull { currency ->
            response.rates[currency]?.let { rate ->
                CurrencyRate(currency, rate)
            }
        }
    }

    fun getMockRates(baseCurrency: String): List<CurrencyRate> {
        return when (baseCurrency) {
            "USD" -> listOf(
                CurrencyRate("SGD", 1.35),
                CurrencyRate("CNY", 7.18),
                CurrencyRate("AUD", 1.52)
            )

            "CNY" -> listOf(
                CurrencyRate("SGD", 0.19),
                CurrencyRate("USD", 0.14),
                CurrencyRate("AUD", 0.21)
            )

            "AUD" -> listOf(
                CurrencyRate("SGD", 0.89),
                CurrencyRate("USD", 0.66),
                CurrencyRate("CNY", 4.72)
            )

            else -> listOf(
                CurrencyRate("CNY", 5.32),
                CurrencyRate("USD", 0.74),
                CurrencyRate("AUD", 1.13)
            )
        }
    }
}