package com.nurig.cryptocurrencylistapp.data

data class CryptocurrencyInfo(
    val description: String,
    val image: CryptocurrencyImage,
    val categories: List<String>,
)