package com.app.todo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDAO {

    @Upsert
    suspend fun updateTodoList(todo :TodoData)

    @Delete
    suspend fun deleteTodoList(todo : TodoData)

    @Query("SELECT * FROM todo")
    fun showAllTodos(): List<TodoData>
}