package com.example.coin.presentation.MarketScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.coin.R
import com.example.coin.ui.components.FormattedChangeInProcent
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CoinCell(coin: CryptocurrencyData, onItemClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onItemClick)
    ) {
        GlideImage(
            model = coin.image,
            contentDescription = coin.name,
            Modifier
                .size(43.dp)
                .background(Color.Transparent)
        )

        Column(modifier = Modifier.padding(start = 6.dp)) {
            Text(text = coin.name, color = colorResource(id = R.color.color_primary_text))
            Text(text = coin.symbol.uppercase(), color = colorResource(id = R.color.color_secondary_text))
        }

        Spacer(modifier = Modifier.weight(1f))

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(text = "\$ " + "${coin.price}", color = colorResource(id = R.color.color_primary_text), fontWeight = FontWeight.Medium)
            FormattedChangeInProcent(price = coin.price, priceChange24h = coin.priceChange24h)
        }
    }
}

@Composable
fun ListCripto(navController: NavController, coins: List<CryptocurrencyData>) {
    LazyColumn() {
        items(coins) {
            coin -> CoinCell(
                coin = coin,
                onItemClick =  {
                    navController.navigate("SpecificCoin/${coin.id}")
                }
            )
        }
    }
}