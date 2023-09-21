package com.app.todo

sealed interface TodoEvents{
    object SaveTodo : TodoEvents
    object ShowDialog : TodoEvents
    object HideDialog : TodoEvents

    data class SetTitle(val setTitle : String) : TodoEvents
    data class SetDescription(val setDescription : String) : TodoEvents


    data class DeleteTodo(val todo: TodoData) : TodoEvents
}