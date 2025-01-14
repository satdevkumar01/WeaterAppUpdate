package com.wipro.weatherapp.presentation.di

import android.app.Application
import com.wipro.weatherapp.domain.usecase.GetWeatherUseCase
import com.wipro.weatherapp.presentation.viewmodel.WeatherViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {
    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getWeatherUseCase: GetWeatherUseCase
    ): WeatherViewModelFactory {
        return WeatherViewModelFactory(
            application,
            getWeatherUseCase
        )
    }

}








