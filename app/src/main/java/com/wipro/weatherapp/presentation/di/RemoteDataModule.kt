package com.wipro.weatherapp.presentation.di

import com.wipro.weatherapp.data.api.WeatherAPIService
import com.wipro.weatherapp.data.repository.dataSource.WeatherDataSource
import com.wipro.weatherapp.data.repository.dataSourceImp.WeatherDataSourceImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RemoteDataModule {

    @Singleton
    @Provides
    fun provideWeatherRemoteDataSource(
        weatherAPIService: WeatherAPIService
    ): WeatherDataSource {
        return WeatherDataSourceImp(weatherAPIService)
    }

}












