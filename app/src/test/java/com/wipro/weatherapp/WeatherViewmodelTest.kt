package com.wipro.weatherapp.features.weather.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.wipro.weatherapp.core.data.WeatherResponse
import com.wipro.weatherapp.core.data.network.ApiInterface
import com.wipro.weatherapp.core.data.network.RetrofitClient
import com.wipro.weatherapp.core.data.utils.DEFAULT_LOCATION
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var weatherViewModel: WeatherViewModel
    private val application: Application = mockk(relaxed = true)
    private val apiInterface: ApiInterface = mockk(relaxed = true)

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        mockkObject(RetrofitClient)
        every { RetrofitClient.service } returns apiInterface
        weatherViewModel = spyk(WeatherViewModel(application))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        unmockkAll()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test loadWeather with network available`() = runTest {
        val weatherResponseMock = WeatherResponse()
        val observer: Observer<WeatherResponse> = mockk(relaxed = true)

        every { weatherViewModel.isNetworkAvailable() } returns true
        coEvery { apiInterface.getCurrentWeatherAsync(any()) } returns mockk {
            coEvery { await() } returns weatherResponseMock
        }

        weatherViewModel.weatherResponse.observeForever(observer)
        weatherViewModel.loadWeather()
        advanceUntilIdle()

        verify { observer.onChanged(weatherResponseMock) }
        weatherViewModel.weatherResponse.removeObserver(observer)
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test loadWeather with network unavailable`() = runTest {
        val observer: Observer<WeatherResponse> = mockk(relaxed = true)

        every { weatherViewModel.isNetworkAvailable() } returns false

        weatherViewModel.weatherResponse.observeForever(observer)
        weatherViewModel.loadWeather()
        advanceUntilIdle()

        verify(exactly = 0) { observer.onChanged(any()) }
        weatherViewModel.weatherResponse.removeObserver(observer)
    }

    @Test
    fun `test isNetworkAvailable when network is available`() {
        val connectivityManager = mockk<ConnectivityManager>(relaxed = true)
        val networkCapabilities = mockk<NetworkCapabilities>(relaxed = true)

        every { application.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns mockk()
        every { connectivityManager.getNetworkCapabilities(any()) } returns networkCapabilities
        every { networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) } returns true

        val result = weatherViewModel.isNetworkAvailable()

        Assert.assertTrue(result)
    }

    @Test
    fun `test isNetworkAvailable when network is unavailable`() {
        val connectivityManager = mockk<ConnectivityManager>(relaxed = true)

        every { application.getSystemService(Context.CONNECTIVITY_SERVICE) } returns connectivityManager
        every { connectivityManager.activeNetwork } returns null

        val result = weatherViewModel.isNetworkAvailable()

        Assert.assertFalse(result)
    }
}
