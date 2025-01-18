package com.wipro.weatherapp.presentation.di

import com.wipro.weatherapp.domain.repository.WeatherRepository
import com.wipro.weatherapp.domain.usecase.GetWeatherUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideGetWeatherUseCase(
        weatherRepository: WeatherRepository
    ): GetWeatherUseCase {
        return GetWeatherUseCase(weatherRepository)
    }

}


















