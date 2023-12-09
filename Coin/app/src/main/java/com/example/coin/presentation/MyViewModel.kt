package com.example.coin.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coin.data.repository.CoinsRepository
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(
    private val coinsRepository: CoinsRepository
): ViewModel() {

    private val _listCoins = MutableStateFlow(value = listOf<CryptocurrencyData>())
    val listCoins: StateFlow<List<CryptocurrencyData>> = _listCoins.asStateFlow()

    init {
        viewModelScope.launch {
            val response = coinsRepository.getList()
            Log.i("DiscoverViewModel", "For test")
            Log.d("DiscoverViewModel", "API Response: $response")
            _listCoins.value = coinsRepository.getList()
        }
    }
}