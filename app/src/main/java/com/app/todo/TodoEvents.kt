package com.app.todo

sealed interface TodoEvents{
    object saveTodo : TodoEvents
    object showDialog : TodoEvents
    object hideDialog : TodoEvents

    data class SetTitle(val setTitle : String) : TodoEvents
    data class SetDescription(val setDescription : String) : TodoEvents
    data class UpdateTodoChecked(val isFinished : Boolean, val todoDataIsChecked: TodoData ) : TodoEvents


    data class deleteTodo(val todo: TodoData) : TodoEvents
}