//package com.example.coin.ui.components
//
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.layout.Column
//import androidx.compose.runtime.Composable
//import coil.compose.rememberImagePainter
//import com.example.coin.presentation.MyViewModel
//import com.nurig.cryptocurrencylistapp.data.CryptocurrencyData
//
//@Composable
//fun ListCripto(coins: List<CryptocurrencyData>, viewModel: MyViewModel) {
//    Column {
//        coins.forEach { coin ->
//            val coinInfo = viewModel.getCoinInfo(coin.id)
//            coinInfo?.let {
//                // Use the coinInfo to place the image or perform other operations
//                // For example, use it to place an image
//                Image(
//                    painter = rememberImagePainter(coinInfo.image), // Use the coinInfo's image property
//                    contentDescription = coin.name
//                )
//            }
//        }
//    }
//}
