package com.example.coin.presentation.SpecificCryptocurrencyScreen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.coin.R

@Composable
fun SwapTime(onTimeFrameSelected: (String) -> Unit) {
    val list = listOf<String>("1D", "7D", "1M", "3M", "1Y", "MAX")

    var selectedButton by remember { mutableStateOf(list[0]) }

    LazyRow(
        contentPadding = PaddingValues(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(list) { text ->
            val isSelected = text == selectedButton
            ButtonCustomUnderChart(
                isSelected = isSelected,
                onClick = {
                    selectedButton = text
                    onTimeFrameSelected(text)
                },
                text = text
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun SwapTimePreview() {
    Row(
        modifier = Modifier.padding(start = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        ButtonCustomUnderChart(true, {}, "1H")
        ButtonCustomUnderChart(false, {}, "24H")
        ButtonCustomUnderChart(false, {}, "1W")
        ButtonCustomUnderChart(false, {}, "1M")
        ButtonCustomUnderChart(false, {}, "6M")
    }
}

@Composable
fun ButtonCustomUnderChart(isSelected: Boolean, onClick: () -> Unit, text: String) {
    OutlinedButton(
        onClick = onClick,
        modifier = Modifier.sizeIn(minWidth = 50.dp, minHeight = 38.dp),
        contentPadding = PaddingValues(0.dp),
        border = choseColorForButtonOutlinedBorder(isSelected),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = colorResource(id = R.color.button_swipe_1),
            contentColor = Color(0xFF6C757D),
            disabledContainerColor = Color(0xFFECF4FF),
            disabledContentColor = Color(0xFF0063F5)
        )
    ) {
        Text(
            text = text,
            color = if (isSelected) colorResource(id = R.color.button_swipe_text_pressed)
            else colorResource(id = R.color.button_swipe_text),
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight(600) else FontWeight(400)
        )
    }
}

fun choseColorForButtonOutlinedBorder(isSelected: Boolean): BorderStroke {
    if (isSelected) return BorderStroke(1.dp, Color(0xFF0063F5))
    return BorderStroke(1.dp, Color(0xFFDFE2E4))
}