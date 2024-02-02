package com.example.everybody.roomdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities =[PeopleEntity::class,WeatherEntity::class], version = 3 )
abstract class LocalDatabase:RoomDatabase() {

    abstract val peopleDao:PeopleDao

    companion object{
        @Volatile
        private var INSTANCE:LocalDatabase ?=null
        fun getInstance(context: Context):LocalDatabase{
            synchronized(this){
                var tempInstance = INSTANCE
                if (tempInstance==null){
                    tempInstance= Room.databaseBuilder(
                        context.applicationContext,
                        LocalDatabase::class.java,
                        "todo_database"
                    ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
                }

                INSTANCE = tempInstance

                return tempInstance
            }
        }
    }
}