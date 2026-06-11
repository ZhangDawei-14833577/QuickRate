package com.example.quickrate

import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {

    @GET("v6/latest/{baseCurrency}")
    suspend fun getLatestRates(
        @Path("baseCurrency") baseCurrency: String
    ): CurrencyResponse
}