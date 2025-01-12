package com.example.plantapp

import android.app.Application
import androidx.room.Room
import com.example.plantapp.database.AppDatabase
import com.example.plantapp.database.AppDatabaseCallback

class MyApp: Application() {
    companion object {
        lateinit var database: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "database"
        )
            .addCallback(AppDatabaseCallback())
            .build()
    }
}