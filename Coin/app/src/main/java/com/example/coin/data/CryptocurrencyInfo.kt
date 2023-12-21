package com.nurig.cryptocurrencylistapp.data

import com.google.gson.annotations.SerializedName

data class DescriptionTranslations(
    val en: String,
    val ru: String,
)

data class CurrentPrice(val usd: Double)

data class CryptocurrencyInfo(
    val id: String,
    val symbol: String,
    val name: String,
    val description: DescriptionTranslations,
    val image: CryptocurrencyImage,
    val categories: List<String>,
    @SerializedName("current_price")
    val currentPrice: CurrentPrice
)