package com.app.todo

data class TodoState(
    val title : String = "",
    val description : String = "",

    val isAddingTodo : Boolean = false,
    val isChecked : Boolean = false,

    val todos : List<TodoData> = emptyList()
)