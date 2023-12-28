package com.example.coin.presentation.SpecificCryptocurrencyScreen

import CryptoPriceChart
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.coin.R
import com.example.coin.data.PriceResponse
import com.example.coin.ui.theme.IconBottomBarDefault
import com.example.coin.ui.theme.IconBottomBarPressed
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyInfo

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ChartGraph(coinInfo: CryptocurrencyInfo, priceResponse: PriceResponse) {

    var selectedTimeFrame by remember { mutableStateOf("3M") }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
    ) {
        Spacer(modifier = Modifier.height(8.dp))
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
                Text(text = coinInfo.symbol.uppercase())
            }
        }
        PriceChange(priceResponse = priceResponse,  selectedTimeFrame = selectedTimeFrame)
        SwapTime { selectedTimeFrame = it }
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            CryptoPriceChart(priceResponse, selectedTimeFrame)
        }
        Button(
            onClick = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            colors = ButtonDefaults.buttonColors(colorResource(id = R.color.button_buy)),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(text = "Buy", color = Color.White)
        }
        Text(
            text = coinInfo.description.en,
            modifier = Modifier
                .padding(all = 16.dp)
                .selectable(true) {}
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenSpecificCryptoCoin(coinInfo: CryptocurrencyInfo, priceResponse: PriceResponse) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    val isStarred = remember {
        mutableStateOf(false)
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Trade ${coinInfo.name}",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /*TODO :: navigation back*/ }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Come back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { isStarred.value = !isStarred.value }) {
                        Icon(
                            imageVector = Icons.Default.FavoriteBorder,
                            contentDescription = "Add to favorites",
                            tint = if (isStarred.value) IconBottomBarPressed else IconBottomBarDefault
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = it.calculateTopPadding())) {
            ChartGraph(coinInfo, priceResponse)
        }
    }
}