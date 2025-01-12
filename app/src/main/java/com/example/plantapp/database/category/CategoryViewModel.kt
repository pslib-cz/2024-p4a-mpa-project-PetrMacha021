package com.example.plantapp.database.category

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CategoryViewModel(private val repository: CategoryRepository): ViewModel() {
    var categories = mutableStateOf<List<Category>>(emptyList())

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            categories.value = repository.getAllCategories()
        }
    }

    fun addCategory(category: Category) {
        viewModelScope.launch {
            repository.addCategory(category)
            loadCategories()
        }
    }

    fun updatePlant(category: Category) {
        viewModelScope.launch {
            repository.updateCategory(category)
            loadCategories()
        }
    }

    fun deleteCategory(category: Category) {
        viewModelScope.launch {
            repository.deleteCategory(category)
            loadCategories()
        }
    }
}