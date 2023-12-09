package com.example.coin.data

import com.example.coin.data.remote.CoinGeckoApi
import com.example.coin.data.repository.CoinsRepository
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    @Singleton
    fun provideGson(): Gson { return GsonBuilder().create() }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient { return OkHttpClient() }

    @Provides
    @Singleton
    fun provideCoinGeckoApi(gson: Gson): CoinGeckoApi {
        return Retrofit.Builder()
            .baseUrl("https://api.coingecko.com/api/v3/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CoinGeckoApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCoinsRepository(coinGeckoApi: CoinGeckoApi): CoinsRepository {
        return CoinsRepository(coinGeckoApi)
    }
}