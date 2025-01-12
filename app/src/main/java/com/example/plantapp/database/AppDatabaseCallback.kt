package com.example.plantapp.database

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.plantapp.MyApp
import com.example.plantapp.database.category.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppDatabaseCallback : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)

        CoroutineScope(Dispatchers.IO).launch {
            val defaultCategories = listOf(
                Category(name = "Indoor Plants"),
                Category(name = "Outdoor Plants"),
                Category(name = "Succulents"),
                Category(name = "Flowering Plants"),
                Category(name = "Herbs")
            )

            val database = MyApp.database
            defaultCategories.forEach { category -> database.categoryDao().insert(category) }
        }
    }
}