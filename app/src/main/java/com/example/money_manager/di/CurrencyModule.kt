package com.example.money_manager.di

import android.content.Context
import com.example.money_manager.data.remote.api.CurrencyApi
import com.example.money_manager.data.repository.CurrencyRepositoryImpl
import com.example.money_manager.domain.repository.CurrenciesRepository
import com.example.money_manager.domain.usecase.currency.ConvertCurrencyUseCase
import com.example.money_manager.domain.usecase.currency.GetAllCurrenciesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CurrencyModule {

    @Provides
    @Singleton
    fun provideCurrencyApi(): CurrencyApi {
        return Retrofit.Builder()
            .baseUrl("http://92.63.96.49:8080/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApi::class.java)
    }

    @Provides
    @Singleton
    fun provideCurrencyRepository(
        currencyApi: CurrencyApi,
        @ApplicationContext context: Context
    ): CurrenciesRepository {
        return CurrencyRepositoryImpl(currencyApi, context)
    }

    @Provides
    @Singleton
    fun provideConvertCurrencyUseCase(repository: CurrenciesRepository): ConvertCurrencyUseCase{
        return ConvertCurrencyUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetAllCurrenciesUseCase(repository: CurrenciesRepository): GetAllCurrenciesUseCase{
        return GetAllCurrenciesUseCase(repository)
    }
}