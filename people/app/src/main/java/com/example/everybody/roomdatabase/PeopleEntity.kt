package com.example.everybody.roomdatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "people_table")
data class PeopleEntity(
    @PrimaryKey(autoGenerate = true)
    val sid: Int = 0,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "first")
    val first: String,

    @ColumnInfo(name = "last")
    val last: String,

    @ColumnInfo(name = "city")
    val city: String,

    @ColumnInfo(name = "country")
    val country: String,

    @ColumnInfo(name = "latitude")
    val latitude: String,

    @ColumnInfo(name = "longitude")
    val longitude: String,

    @ColumnInfo(name = "dob")
    val dob: String,

    @ColumnInfo(name = "cell")
    val cell: String,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "large_picture")
    val largePicture: String
)
