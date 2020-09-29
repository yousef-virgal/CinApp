package com.example.taskweek4.data.models.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.taskweek4.data.models.ui.Movies

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addMovies(list: List<Movies>)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun changeMovie(movie: Movies)
    @Query("SELECT * FROM Movies")
    fun getMovies():List<Movies>
    @Query("SELECT * FROM Movies WHERE fav == :trueFav")
    fun getFavorites(trueFav:Boolean = true):List<Movies>
    @Query("DELETE FROM Movies")
    fun deleteAll()

}