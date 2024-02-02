package com.example.everybody.retrofitAPIs.randomuserAPI

import retrofit2.Response
import retrofit2.http.GET

interface RandomUser {
    @GET("/api/?results=50")
    suspend fun getAllUsers():Response<UserDetails>
}