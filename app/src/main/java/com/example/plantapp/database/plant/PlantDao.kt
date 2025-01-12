package com.example.plantapp.database.plant

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Insert
    suspend fun insert(plant: Plant): Long

    @Query("SELECT * FROM plants")
    suspend fun getAll(): List<Plant>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun get(id: Int): Plant?

    @Update
    suspend fun update(plant: Plant)

    @Delete
    suspend fun delete(plant: Plant)
}