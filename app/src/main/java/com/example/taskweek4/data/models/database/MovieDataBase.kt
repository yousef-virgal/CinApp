package com.example.taskweek4.data.models.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.taskweek4.data.models.ui.Movies

@Database(entities = [Movies::class],version = 1, exportSchema = false)
abstract class MovieDataBase:RoomDatabase() {

    abstract fun movieDao():MovieDao
    companion object{
        private var Instance:MovieDataBase? =null

        fun initializeDataBase(context:Context):MovieDataBase{
            if(Instance!=null)
                return Instance!!
            Instance = Room.databaseBuilder(
                context.applicationContext,
                MovieDataBase::class.java, "movie_db"
            ).allowMainThreadQueries().build()
            return Instance!!

        }
    }
}