package com.app.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateTodoList(todo :TodoData)

    @Delete
    suspend fun deleteTodoList(todo : TodoData)


    @Query("SELECT * FROM todo")
    fun showAllTodos(): Flow<List<TodoData>>

}