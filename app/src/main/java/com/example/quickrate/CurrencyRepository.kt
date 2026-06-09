package com.example.quickrate

class CurrencyRepository {

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