package com.example.uas_android.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note_table")
data class Movies(

    @PrimaryKey val id: Int,
    val title: String,
    val description: String,
    val imageUrl: String
)
