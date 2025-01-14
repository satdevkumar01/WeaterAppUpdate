package com.wipro.weatherapp.data


object RetrofitClient {
    /*
        val service: ApiInterface by lazy {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url()
                    .newBuilder()
                    .addQueryParameter(ACCESS_KEY, API_KEY)
                    .build()
                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()
                return@Interceptor chain.proceed(request)

            }

            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            val okhttp = OkHttpClient.Builder().addInterceptor(requestInterceptor)
                .addNetworkInterceptor(interceptor).build()

            Retrofit.Builder()
                .client(okhttp)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build().create(ApiInterface::class.java)
        }
    */

}