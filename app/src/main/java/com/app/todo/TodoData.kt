package com.app.todo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Todo")
data class TodoData(
    val title: String,
    val description : String,
    val isChecked : Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0
)
