package com.app.todo

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine

import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoMainViewModel(
    private val dao : TodoDAO
) : ViewModel() {



    private val _state = mutableStateOf(TodoState())
    val state : State<TodoState> = _state


    private fun getAllTodos(){
        var savedTodos = emptyList<TodoData>()
         viewModelScope.launch {
             savedTodos = dao.showAllTodos()
         }

        _state.value = state.value.copy(
            title = "",
            description = "",
            isAddingTodo = false,
            todos = state.value.todos + savedTodos
        )
    }

    fun onEvent(event : TodoEvents){
        when(event){
            is TodoEvents.SetDescription -> {
                _state.value = state.value.copy(
                    description = event.setDescription
                )
            }

            is TodoEvents.SetTitle -> {
                _state.value = state.value.copy(
                    title = event.setTitle
                )
            }
            is TodoEvents.DeleteTodo -> {
                viewModelScope.launch {
                    dao.deleteTodoList(event.todo)
                }
            }
            TodoEvents.SaveTodo -> {
                val title = state.value.title
                val description = state.value.description

                if (title.isBlank() || description.isBlank() ) return

                val todo = TodoData(
                    title = title,
                    description = description
                )
                viewModelScope.launch {
                    dao.updateTodoList(todo)
                }

                _state.value = state.value.copy(
                    title = "",
                    description = "",
                    isAddingTodo = false,
                    todos = state.value.todos + todo
                )
            }
            TodoEvents.HideDialog -> {
                _state.value = state.value.copy(
                    isAddingTodo = false

                )
            }
            TodoEvents.ShowDialog ->  {
                _state.value = state.value.copy(
                    isAddingTodo = true

                )
            }
        }
    }
}