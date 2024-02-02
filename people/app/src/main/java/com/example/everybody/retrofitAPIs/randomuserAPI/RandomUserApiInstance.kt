package com.example.everybody.retrofitAPIs.randomuserAPI

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

object RandomUserApiInstance {
    val api : RandomUser by lazy {
//        val interceptor = HttpLoggingInterceptor()
//        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        val client = OkHttpClient.Builder()
//            .addInterceptor(interceptor)
//            .build()

        Retrofit.Builder()      // create instance for api interface
            .baseUrl("https://randomuser.me")
            .addConverterFactory(GsonConverterFactory.create())
//            .client(client)
            .build().create(RandomUser::class.java) // create instance for api interface
    }
}