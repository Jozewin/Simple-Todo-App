package com.app.todo

data class TodoState(
    val title : String = "",
    val description : String = "",

    val isAddingTodo : Boolean = false,

    val todos : List<TodoData> = emptyList()
)