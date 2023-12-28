package com.example.coin.ui.components


import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FormattedChangeInProcent(price: Double, priceChange24h: Double) {

    val percent = ((price + priceChange24h)-price)/price*100
    val roundedPercent = String.format("%.2f", percent)

    val textColor = when {
        percent < 0 -> Color(0xFFEB5757) // Text color for negative percentage
        else -> Color(0xFF2A9D8F) // Text color for positive or zero percentage
    }

    Text(
        text = "$roundedPercent %",
        color = textColor,
        fontWeight = FontWeight.Medium
    )
}


@Composable
fun FormattedChangeForChart(price: Double, priceChange: Double) {

    val percent = ((price + priceChange) - price) / price * 100
    val roundedPercent = String.format("%.2f", percent)

    val textColor = when {
        percent < 0 -> Color(0xFFEB5757) // Text color for negative percentage
        else -> Color(0xFF2A9D8F) // Text color for positive or zero percentage
    }

    val triangleSymbol = if (percent >= 0) "\u25B2" else "\u25BC"
    val triangleColor = if (percent >= 0) Color(0xFF2A9D8F) else Color(0xFFEB5757)

    Row(verticalAlignment = Alignment.CenterVertically) {

        Text(
            text = triangleSymbol,
            color = triangleColor,
            fontSize = 20.sp,
            modifier = Modifier.padding(end = 4.dp) // Добавьте отступ между текстом и треугольником
        )
        Text(
            text = "$roundedPercent%",
            color = textColor,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp,
        )
    }
}
