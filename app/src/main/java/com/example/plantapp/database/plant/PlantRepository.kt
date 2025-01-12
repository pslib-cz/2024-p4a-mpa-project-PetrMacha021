package com.example.plantapp.database.plant

class PlantRepository(private val dao: PlantDao) {
    suspend fun addPlant(plant: Plant) = dao.insert(plant)
    suspend fun getAllPlants() = dao.getAll()
    suspend fun getPlant(id: Int) = dao.get(id)
    suspend fun updatePlant(plant: Plant) = dao.update(plant)
    suspend fun deletePlant(plant: Plant) = dao.delete(plant)
}