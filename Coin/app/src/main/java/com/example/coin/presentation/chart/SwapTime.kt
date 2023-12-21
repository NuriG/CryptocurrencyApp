package com.example.coin.presentation.chart

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SwapTime(onTimeFrameSelected: (String) -> Unit) {
    val list = listOf<String>("1 day", "7 days", "1 month", "3 months", "1 year", "max")

    var selectedButton by remember { mutableStateOf(list[0]) }

    LazyRow {
        items(list) { text ->
            val isSelected = text == selectedButton

            Button(
                onClick = {
                    selectedButton = text
                    onTimeFrameSelected(text)
                },
                colors = if (isSelected) ButtonDefaults.buttonColors(containerColor = Color(0xFF4A4AFB))
                else ButtonDefaults.buttonColors(containerColor = Color(0xFFB3AFFF)),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = text)
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SwapTimePreview() {
    SwapTime { }
}