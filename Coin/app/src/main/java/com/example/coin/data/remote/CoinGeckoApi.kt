package com.example.coin.data.remote

import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyInfo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CoinGeckoApi {

    @GET("coins/markets")
    suspend fun getCryptocurrencyList(@Query("vs_currency") vc: String = "usd"): List<CryptocurrencyData>

    @GET("coins/{id}")
    suspend fun getInfoCoin(@Path("id") id: String): CryptocurrencyInfo
}