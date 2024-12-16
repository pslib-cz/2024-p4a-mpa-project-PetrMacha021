package com.example.plantapp.database.plant

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plants")
data class Plant(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val sowingDate: Long,
    val growingTime: Long
)