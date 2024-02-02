package com.example.everybody.retrofitAPIs.weatherAPIs

import com.example.everybody.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {

    @GET("v1/current.json?key="+BuildConfig.api_key+"&aqi=yes")
    suspend fun getUserCurrentWeather(@Query("q")q:String):Response<WeatherData>
}