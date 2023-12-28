package com.example.coin.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coin.R
import com.example.coin.presentation.MarketScreen.MarketScreen
import com.example.coin.presentation.SpecificCryptocurrencyScreen.ScreenSpecificCryptoCoin
import com.example.coin.presentation.SpecificCryptocurrencyScreen.ToManyRequest
import com.example.coin.ui.theme.IconBottomBarPressed

data class BottomNavigationItem(
    val title: String,
    val icon: ImageVector,
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CryptocurrencyApp(
    viewModel: MyViewModel = hiltViewModel<MyViewModel>(),
    navController: NavHostController = rememberNavController()
) {
    val coinsState by viewModel.listCoins.collectAsState()

    Scaffold(
        bottomBar = {
            MyBottomBar(navController = navController)
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "Discover",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("Discover") {
                MarketScreen(navController = navController ,coins = coinsState)
            }
            composable("SpecificCoin/{coinId}") { backStackEntry ->
                val coinId = backStackEntry.arguments?.getString("coinId")

                val selectedCoinInfo by remember(coinId) { viewModel.selectedCoinInfo }
                    .collectAsState(initial = null)

                val selectedCoinPrices by remember(coinId) { viewModel.listPricesForChart }
                    .collectAsState(initial = null)

                LaunchedEffect(key1 = coinsState) {
                    viewModel.loadCoinInfo(coinId!!)
                    viewModel.loadCoinPricesForChart(coinId)
                }

                if (selectedCoinPrices == null || selectedCoinInfo == null) {
                    ToManyRequest()
                } else {
                    ScreenSpecificCryptoCoin(coinInfo = selectedCoinInfo!!, priceResponse = selectedCoinPrices!!)
                }

            }
            composable("Favorites") {
            }
            composable("Profile") {
            }
            composable("SpecificCoin") {}
        }
    }
}


@Composable
fun MyBottomBar(navController: NavHostController) {
    val discoverIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.market_icon)
    val favoritesIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.portfolio_icon)
    val profileIcon: ImageVector = ImageVector.vectorResource(id = R.drawable.profile_icon)

    val items = listOf(
        BottomNavigationItem("Discover", discoverIcon),
        BottomNavigationItem("Favorites", favoritesIcon),
        BottomNavigationItem("Profile", profileIcon),
    )

    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(0)
    }

    NavigationBar(
        containerColor = colorResource(id = R.color.color_bottom_bar),
        modifier = Modifier.shadow(4.dp)
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = { navController.navigate(item.title); selectedItemIndex = index },
                label = { Text(text = item.title)},
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = colorResource(id = R.color.icon_bottom_bar_pressed),
                    selectedTextColor = colorResource(id = R.color.icon_bottom_bar_pressed),
                    indicatorColor = colorResource(id = R.color.color_bottom_bar)
                )

            )
        }
    }

}