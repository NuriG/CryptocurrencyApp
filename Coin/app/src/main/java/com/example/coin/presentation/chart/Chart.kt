package com.example.coin.presentation.chart

import CryptoPriceChart
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.coin.data.PriceResponse
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyInfo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChartGraph(coinInfo: CryptocurrencyInfo, priceResponse: PriceResponse) {

    var selectedTimeFrame by remember { mutableStateOf("1 day") }
    val scrollState = rememberScrollState()
    Column(modifier = Modifier.verticalScroll(scrollState)) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
            ) {
            Column(
                modifier = Modifier.align(alignment = Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                GlideImage(
                    model = coinInfo.image.large,
                    contentDescription = null,
                    modifier = Modifier.size(70.dp)
                )
                Text(text = coinInfo.name)
                Text(text = coinInfo.symbol.uppercase(), color = Color(0xFF9B9B9B))
            }
        }
        PriceChange(priceResponse = priceResponse,  selectedTimeFrame = selectedTimeFrame)
        SwapTime { selectedTimeFrame = it }
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
        ) {
            CryptoPriceChart(priceResponse, selectedTimeFrame)
        }
        Button(
            onClick = {  },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00A08F)),
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)
        ) {
            Text(text = "Buy")
        }
        Text(
            text = coinInfo.description.en,
            modifier = Modifier.padding(all = 5.dp).selectable(true) {}
        )
    }
}