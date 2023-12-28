package com.example.coin.presentation.SpecificCryptocurrencyScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coin.R

@Composable
fun ToManyRequest() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.http_429_image),
            contentDescription = "http error 429 so many request",
            Modifier.size(360.dp)
        )
        Text(text = "Please wait 1 minute and try again")
    }

}

@Preview(showSystemUi = true)
@Composable
fun ToManyRequestPreview() {
    ToManyRequest()
}