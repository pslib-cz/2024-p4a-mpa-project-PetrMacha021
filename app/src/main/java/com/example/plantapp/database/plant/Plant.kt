package com.example.plantapp.database.plant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String,
    val sowingDate: Int,
    val growingTime: Int
)