package com.example.plantapp.database.plant

class PlantRepository(private val dao: PlantDao) {
    suspend fun addPlant(plant: Plant) = dao.insertPlant(plant)
    suspend fun getAllPlants() = dao.getAllPlants()
    suspend fun getPlant(id: Int) = dao.getPlant(id)
    suspend fun updatePlant(plant: Plant) = dao.updatePlant(plant)
    suspend fun deletePlant(plant: Plant) = dao.deletePlant(plant)
}