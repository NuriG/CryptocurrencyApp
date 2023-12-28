package com.example.coin.presentation.SpecificCryptocurrencyScreen

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coin.data.PriceResponse
import com.example.coin.ui.components.FormattedChangeForChart

@Composable
fun PriceChange(priceResponse: PriceResponse,  selectedTimeFrame: String) {

    val prices = priceResponse.prices.sortedBy { it[0] }

    val filteredPrices = when (selectedTimeFrame) {
        "1D" -> prices.filter { it[0] >= (System.currentTimeMillis() - 24 * 60 * 60 * 1000) }
        "7D" -> prices.filter { it[0] >= (System.currentTimeMillis() - 7 * 24 * 60 * 60 * 1000) }
        "1M" -> prices.filter { it[0] >= (System.currentTimeMillis() - 30L * 24L * 60L * 60L * 1000L) }
        "3M" -> prices.filter { it[0] >= (System.currentTimeMillis() - 90L * 24L * 60L * 60L * 1000L) }
        "1Y" -> prices.filter { it[0] >= (System.currentTimeMillis() - 365L * 24L * 60L * 60L * 1000L) }
        "MAX" -> prices
        else -> prices
    }

    val initialPrice = filteredPrices.firstOrNull()?.get(1) // Цена в начале промежутка
    val currentPrice = prices.lastOrNull()?.get(1) // Текущая цена

    val currentPriceTwo = filteredPrices.lastOrNull()?.get(1) // Текущая цена

    val currentPriceRound = roundNumber(currentPrice!!)

    val priceChange = currentPrice - initialPrice!!

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = currentPriceRound.toString(), fontSize = 20.sp)

        Spacer(modifier = Modifier.weight(1f))

        FormattedChangeForChart(price = currentPrice, priceChange = priceChange)
    }
}

@Preview
@Composable
fun PriceChangePreview() {
    val testData = PriceResponse(
        prices = listOf(
            listOf(1702905628269.0, 41062.35792800487),
            listOf(1702905925784.0, 41086.62748275064),
            listOf(1702906273389.0, 41149.07771941036)
        ),
        marketCaps = emptyList(),
        totalVolumes = emptyList()
    )
    PriceChange(testData, "1 day")
}