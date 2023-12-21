package com.example.coin.presentation

import CryptoPriceChart
import android.util.Log
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.coin.presentation.chart.ChartGraph
import com.example.coin.presentation.chart.ToManyRequest

data class BottomNavigationItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptocurrencyApp() {

    val viewModel = hiltViewModel<MyViewModel>()
    val coinsState by viewModel.listCoins.collectAsState()

    val items = listOf(
        BottomNavigationItem("Discover", Icons.Filled.List, Icons.Default.List ),
        BottomNavigationItem("Favorites", Icons.Filled.Star, Icons.Default.Star ),
        BottomNavigationItem("Profile", Icons.Filled.Home, Icons.Default.Home ),
    )

    val navController = rememberNavController()
    var selectedItemIndex by rememberSaveable {
        mutableStateOf(0)
    }
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                // val currentDestination = navBackStackEntry?.destination
                items.forEachIndexed { index, item ->
                    NavigationBarItem(
                        selected = selectedItemIndex == index,
                        onClick = { navController.navigate(item.title) },
                        label = { Text(text = item.title)},
                        icon = {
                            Icon(
                                imageVector = if (index == selectedItemIndex) {
                                    item.selectedIcon
                                } else item.unselectedIcon,
                                contentDescription = item.title)
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Discover",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Discover") { ListCripto(navController = navController ,coins = coinsState) }
            composable("SpecificCoin/{coinId}") { backStackEntry ->
                val coinId = backStackEntry.arguments?.getString("coinId")
                Log.i("ClickSpecificCoin", "For test")

                LaunchedEffect(key1 = Unit) {
                    viewModel.loadCoinInfo(coinId!!)
                    viewModel.loadCoinPricesForChart(coinId!!)
                }
                val selectedCoinInfo by viewModel.selectedCoinInfo.collectAsState()
                val selectedCoinPrices by viewModel.listPricesForChart.collectAsState()

                Log.d("ClickSpecificCoin", "API Response coinInfo: $selectedCoinInfo")
                Log.d("ClickSpecificCoin", "API Response coinPrices: $selectedCoinPrices")

                if (selectedCoinPrices == null || selectedCoinInfo == null) {
                    ToManyRequest()
                } else {
                    ChartGraph(coinInfo = selectedCoinInfo!!, priceResponse = selectedCoinPrices!!)
                }

            }
            composable("Favorites") {}
            composable("Profile") {}
            composable("SpecificCoin") {}
        }
    }
}
