package com.nurig.cryptocurrencylistapp.data

import com.google.gson.annotations.SerializedName

data class CryptocurrencyData(
    val id: String,
    val symbol: String,
    val name: String,
    val image: String,
    @SerializedName("current_price")
    val price: Double,
    @SerializedName("price_change_24h")
    val priceChange24h: Double,
)