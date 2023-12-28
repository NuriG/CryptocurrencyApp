package com.example.coin.presentation.MarketScreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import com.example.coin.R
import com.example.coin.ui.theme.ButtonTextUnpressed
import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MarketScreen(navController: NavController, coins: List<CryptocurrencyData>) {
    val listTab = listOf("All", "Gainer", "Loser")
    val pagerState = rememberPagerState(0) {
        listTab.size
    }
    val scope = rememberCoroutineScope()
    val selectedTabIndex = remember { derivedStateOf { pagerState.currentPage } }


    Column {
        TabRow(
            selectedTabIndex = selectedTabIndex.value,
            modifier = Modifier.fillMaxWidth()
        ) {
            listTab.forEachIndexed { index, currentTab ->
                Tab(
                    selectedContentColor = colorResource(id = R.color.icon_bottom_bar_pressed),
                    unselectedContentColor = ButtonTextUnpressed,
                    selected = selectedTabIndex.value == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        Text(
                            text = currentTab,
                            fontWeight = if (selectedTabIndex.value == index)
                                FontWeight(700) else FontWeight(400),
                            color = colorResource(id = R.color.icon_bottom_bar_pressed)
                        )
                    },
                )
            }
        }
        HorizontalPager(state = pagerState) { page ->
            val sortedCoinsList = when (page) {
                0 -> coins
                1 -> coins.filter { it.priceChange24h >= 0 }
                2 -> coins.filter { it.priceChange24h < 0 }
                else -> coins
            }
            ListCripto(navController = navController, coins = sortedCoinsList)
        }
    }
}