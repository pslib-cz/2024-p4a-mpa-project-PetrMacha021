package com.example.plantapp.database.plant

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlantViewModel(private val repository: PlantRepository): ViewModel() {
    var plants = mutableStateOf<List<Plant>>(emptyList())

    init {
        loadPlants()
    }

    private fun loadPlants() {
        viewModelScope.launch {
            plants.value = repository.getAllPlants()
        }
    }

    fun addPlant(plant: Plant) {
         viewModelScope.launch {
             repository.addPlant(plant)
             loadPlants()
         }
    }

    fun updatePlant(plant: Plant) {
        viewModelScope.launch {
            repository.updatePlant(plant)
            loadPlants()
        }
    }

    fun deletePlant(plant: Plant) {
        viewModelScope.launch {
            repository.deletePlant(plant)
            loadPlants()
        }
    }
}