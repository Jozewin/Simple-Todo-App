package com.app.todo

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(
    entities = [TodoData::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase(){
    abstract val dao : TodoDAO
}

