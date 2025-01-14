package com.wipro.weatherapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIServiceTest {
    private lateinit var service: WeatherAPIService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPIService::class.java)
    }


    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()
        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }



    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("weatherresponse.json")
            val responseBody = service.getCurrentWeatherAsync("New York", ).body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/current?access_key=f3db30618a38d250aa6987ca9cfb64e6&%20query=New%20York")
        }

    }

    @Test
    fun getTopHeadline_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("weatherresponse.json")
            val responseBody = service.getCurrentWeatherAsync("New York", ).body()
            val weatherCode = responseBody!!.current?.weatherCode
            assertThat(weatherCode).isEqualTo(113)

        }
    }

    @Test
    fun getTopHeadline_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("weatherresponse.json")
            val responseBody = service.getCurrentWeatherAsync("New York").body()
            val pressure = responseBody!!.current?.pressure
            assertThat(pressure).isEqualTo("1015")
       }
    }

    @After
    fun tearDown() {
        server.shutdown()

    }


}