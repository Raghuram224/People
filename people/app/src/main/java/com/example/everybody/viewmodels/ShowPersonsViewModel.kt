package com.example.everybody.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.everybody.retrofitAPIs.randomuserAPI.Coordinates
import com.example.everybody.retrofitAPIs.randomuserAPI.Dob
import com.example.everybody.retrofitAPIs.randomuserAPI.Location
import com.example.everybody.retrofitAPIs.randomuserAPI.Name
import com.example.everybody.retrofitAPIs.randomuserAPI.Picture

import com.example.everybody.retrofitAPIs.randomuserAPI.User
import com.example.everybody.retrofitAPIs.randomuserAPI.RandomUserApiInstance
import com.example.everybody.retrofitAPIs.randomuserAPI.UserDetails
import com.example.everybody.retrofitAPIs.weatherAPIs.WeatherApiInstance
import com.example.everybody.retrofitAPIs.weatherAPIs.WeatherData
import com.example.everybody.roomdatabase.LocalDatabase
import com.example.everybody.roomdatabase.PeopleDao
import com.example.everybody.roomdatabase.PeopleEntity
import com.google.gson.Gson

import retrofit2.Response


class ShowPersonsViewModel(application: Application):ViewModel() {
    val userDataList= mutableListOf<User>()
    var weatherStatus :String?=null
    var weatherImg:String? =null
    lateinit var passingData:User
    lateinit var weatherDetails:Response<WeatherData>
    var  searchFromApi=false
    private var peopleDao:PeopleDao

    private var usersDatas = ArrayList<User>()
    lateinit var dbData:List<User>
    init {
        peopleDao = LocalDatabase.getInstance(application).peopleDao
    }

    fun decideToFetchDataFrom():Boolean{
        println("dao"+peopleDao.getAllUsers())
        return peopleDao.getAllUsers().size>0
    }
    suspend fun randomUserApiCall(){
        getUserInfo()

    }
    suspend fun userCurrentWeather(coOrdinates: String){

        getUserWeather(coOrdinates)


    }

    //Api parts

    suspend fun getUserInfo() {

        val responseInside = RandomUserApiInstance.api.getAllUsers()

        if (responseInside.isSuccessful) {
            searchFromApi=true
            convertJsonToArrayOfUsers(responseInside)


        }else{
            println(responseInside.errorBody())
        }
    }

    private fun convertJsonToArrayOfUsers(response: Response<UserDetails>){
        val usersList = response.body()!!
        for (user in usersList.results){
            userDataList.add(user)

            insertIntoDB(user)

        }



    }

    // Weather parts

    private suspend  fun getUserWeather(coOrdinates:String){
        val response = WeatherApiInstance.weatherApi.getUserCurrentWeather(coOrdinates)
        if (response.isSuccessful){
            Log.i("Weather response success",response.body().toString())

            weatherDetails =response
            usersWeatherDetails(response)

        }else{
            Log.i("Weather response error",response.errorBody().toString())
        }

        println(response.body())
    }

    private fun usersWeatherDetails(response: Response<WeatherData>){
        var data = response.body()?.current?.tempC.toString().substring(0,2)+"° "
        data += response.body()?.location?.name.toString()+"\n\t"
        data+=response.body()?.current?.condition?.text.toString()
//        println("weather"+data+"***"+response.body()?.current?.condition?.icon.toString())
        weatherStatus = data
        weatherImg="http:"+response.body()?.current?.condition?.icon.toString()
    }

    fun getAirQuality(airQuality:Int):String{
        return when(airQuality){
            1 -> "Good"
            2 -> "Moderate"
            3 -> "Unhealthy for sensitive people"
            4 -> "Unhealthy"
            5 -> "Very Unhealthy"
            6 -> "Hazardous"
            else -> {"undefined"}
        }
    }


    // Db related parts

    private fun insertIntoDB(user: User){
        peopleDao.insertUsers(PeopleEntity(
            gender = user.gender,
            title = user.name.title,
            first = user.name.first,
            last = user.name.last,
            city = user.location.city,
            country = user.location.country,
            latitude = user.location.coordinates.latitude,
            longitude = user.location.coordinates.longitude,
            dob = user.dob.date,
            cell = user.cell,
            email = user.email,
            largePicture = user.picture.large
        ))
    }
    fun convertEntityToData():List<User>{
        val entities= getAllDataFromDb()
        for (data in entities){

            val name  = Name(data.title,data.first,data.last)
            val coOrdinates = Coordinates(data.latitude,data.longitude)
            val location = Location(data.city,data.country,coOrdinates)
            val dob = Dob(data.dob)
            val picture = Picture(data.largePicture)


            usersDatas.add(User(
                gender = data.gender,
                name = name,
                location = location,
                email = data.email,
                dob = dob,
                cell = data.cell,
                picture =picture))
        }
        return usersDatas
    }

    private fun getAllDataFromDb():List<PeopleEntity>{
        return peopleDao.getAllUsers()
    }


}