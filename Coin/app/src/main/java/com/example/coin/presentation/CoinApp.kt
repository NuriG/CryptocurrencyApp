package com.example.coin.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun CoinApp() {

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "CoinList") {
        //composable("CoinList") { ListCripto(coins = SampleData.coinListTest) }
        composable("Favorites") {}
        composable("Profile") {}
        composable("SpecificCoin") {}
    }
}