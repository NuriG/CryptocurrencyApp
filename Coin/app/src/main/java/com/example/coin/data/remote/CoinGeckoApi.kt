package com.example.coin.data.remote

import com.example.coin.data.PriceResponse
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getCryptocurrencyList(
        @Query("vs_currency") vc: String = "usd"
    ): List<CryptocurrencyData>

    @GET("coins/{id}")
    suspend fun getInfoCoin(@Path("id") id: String): CryptocurrencyInfo

    @GET("coins/{id}/market_chart")
    suspend fun getInfoChart(
        @Path("id") id: String,
        @Query("vs_currency") vc: String = "usd",
        @Query("days") days: String = "max"
    ): PriceResponse
}