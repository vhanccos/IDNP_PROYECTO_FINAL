package com.example.myapplication001.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication001.data.local.entity.MuseumEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MuseumDao {
    @Query("SELECT * FROM museums")
    fun getAllMuseums(): Flow<List<MuseumEntity>>

    @Query("SELECT * FROM museums WHERE id = :id")
    fun getMuseumById(id: String): Flow<MuseumEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(museums: List<MuseumEntity>)
}
