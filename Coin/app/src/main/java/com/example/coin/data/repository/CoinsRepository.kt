package com.example.coin.data.repository

import com.example.coin.data.remote.CoinGeckoApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class CoinsRepository @Inject constructor(private val coinGeckoApi: CoinGeckoApi) {

    suspend fun getList() = coinGeckoApi.getCryptocurrencyList()

    // функция для экрана конкретной криптовалюты
    suspend fun getInfoCoin(id: String) = coinGeckoApi.getInfoCoin(id)


}
