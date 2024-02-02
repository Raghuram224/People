package com.example.everybody.roomdatabase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PeopleDao{
    @Insert
    fun insertUsers(peoples:PeopleEntity)
    @Query("select * from people_table")
    fun getAllUsers(): MutableList<PeopleEntity>

    @Insert
    fun insertWeatherReports(weatherEntity: WeatherEntity)

    @Query("select * from weather_table")
    fun getAllWeatherReports():MutableList<WeatherEntity>

}