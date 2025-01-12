package com.example.plantapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.plantapp.database.category.Category
import com.example.plantapp.database.category.CategoryDao
import com.example.plantapp.database.plant.Plant
import com.example.plantapp.database.plant.PlantDao

@Database(entities = [Plant::class, Category::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun plantDao(): PlantDao
    abstract fun categoryDao(): CategoryDao
}