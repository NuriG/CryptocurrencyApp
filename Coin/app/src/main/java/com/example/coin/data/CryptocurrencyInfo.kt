package com.nurig.cryptocurrencylistapp.data
data class DescriptionTranslations(
    val en: String,
    val ru: String,
)
data class CryptocurrencyInfo(
    val description: DescriptionTranslations,
    val image: CryptocurrencyImage,
    val categories: List<String>,
)