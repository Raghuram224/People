package com.example.everybody.retrofitAPIs.randomuserAPI

import com.google.gson.annotations.SerializedName

data class UserDetails(
    @SerializedName("results")
    val results:List<User>
)

data class User(
    @SerializedName("gender")
    val gender: String,

    @SerializedName("name")
    val name: Name,

    @SerializedName("location")
    val location: com.example.everybody.retrofitAPIs.randomuserAPI.Location,

    @SerializedName("email")
    val email: String,

    @SerializedName("dob")
    val dob: Dob,


    @SerializedName("cell")
    val cell: String,

    @SerializedName("picture")
    val picture: Picture,


    )

data class Name(
    @SerializedName("title")
    val title: String,

    @SerializedName("first")
    val first: String,

    @SerializedName("last")
    val last: String
)

data class Location(
    @SerializedName("city")
    val city: String,


    @SerializedName("country")
    val country: String,



    @SerializedName("coordinates")
    val coordinates: Coordinates,


)

data class Coordinates(
    @SerializedName("latitude")
    val latitude: String,

    @SerializedName("longitude")
    val longitude: String
)

data class Dob(
    @SerializedName("date")
    val date: String,


)

data class Picture(
    @SerializedName("large")
    val large: String,


)