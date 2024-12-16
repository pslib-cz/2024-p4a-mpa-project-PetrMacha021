package com.example.plantapp.database.plant

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface PlantDao {
    @Insert
    suspend fun insertPlant(plant: Plant): Long

    @Query("SELECT * FROM plants")
    suspend fun getAllPlants(): List<Plant>

    @Query("SELECT * FROM plants WHERE id = :id")
    suspend fun getPlant(id: Int): Plant?

    @Update
    suspend fun updatePlant(plant: Plant)

    @Delete
    suspend fun deletePlant(plant: Plant)
}