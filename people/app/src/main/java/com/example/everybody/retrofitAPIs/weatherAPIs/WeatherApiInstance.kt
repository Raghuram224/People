package com.example.everybody.retrofitAPIs.weatherAPIs

import com.example.everybody.retrofitAPIs.randomuserAPI.UserDetails
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object WeatherApiInstance {

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl("http://api.weatherapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(WeatherApi::class.java)
    }

}