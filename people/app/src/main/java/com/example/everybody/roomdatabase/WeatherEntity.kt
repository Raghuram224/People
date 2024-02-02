package com.example.everybody.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weather_table")
data class WeatherEntity(
    @PrimaryKey(autoGenerate = true)
    val sid:Int=0,
    @ColumnInfo(name = "weather_reports")
    val weather:String

)