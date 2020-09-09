package com.example.taskweek4.data.models.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskweek4.data.models.ui.Movies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovies(list: List<Movies>)
    @Query("SELECT * FROM Movies")
    fun getMovies():List<Movies>
}