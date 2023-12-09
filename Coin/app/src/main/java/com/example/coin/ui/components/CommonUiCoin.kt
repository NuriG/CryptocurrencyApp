package com.example.coin.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun FormattedChangeInProcent(price: Double, priceChange24h: Double) {

    val procent = ((price + priceChange24h)-price)/price*100
    val roundedProcent = String.format("%.2f", procent)

    val textColor = when {
        procent < 0 -> Color(0xFFEB5757) // Text color for negative percentage
        else -> Color(0xFF2A9D8F) // Text color for positive or zero percentage
    }

    Text(
        text = roundedProcent + " %",
        color = textColor,
        fontWeight = FontWeight.Medium
    )
}