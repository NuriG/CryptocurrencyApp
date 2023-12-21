package com.example.coin.data.repository

import com.example.coin.data.PriceResponse
import com.example.coin.data.remote.CoinGeckoApi
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyInfo
import retrofit2.HttpException
import javax.inject.Inject

class CoinsRepository @Inject constructor(private val coinGeckoApi: CoinGeckoApi) {
    suspend fun getList() = coinGeckoApi.getCryptocurrencyList()

    suspend fun getInfoCoin(id: String): CryptocurrencyInfo? {
        return try {
            coinGeckoApi.getInfoCoin(id)
        } catch (e: HttpException) {
            // so many request due to free Api
            if (e.code() == 429) {
                null
            } else {
                throw e
            }
        }
    }

    suspend fun getInfoPricesForChart(id: String): PriceResponse? {
        return try {
            coinGeckoApi.getInfoChart(id)
        } catch (e: HttpException) {
            if (e.code() == 429) {
                null
            } else {
                throw e
            }
        }
    }

}
