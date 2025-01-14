package com.wipro.weatherapp.presentation.di

import com.wipro.weatherapp.data.WeatherRepositoryImp
import com.wipro.weatherapp.data.repository.dataSource.WeatherDataSource
import com.wipro.weatherapp.domain.repository.WeatherRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideWeatherRepository(
        weatherRemoteDataSource: WeatherDataSource,
    ): WeatherRepository {
        return WeatherRepositoryImp(
            weatherRemoteDataSource
        )
    }

}














