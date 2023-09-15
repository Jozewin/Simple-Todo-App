package com.app.todo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted

import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoMainViewModel(
    private val dao : TodoDAO
) : ViewModel() {



        private val _state = MutableStateFlow(TodoState())
        val state = _state.stateIn(viewModelScope, SharingStarted.Lazily, TodoState())


    fun onEvent(event : TodoEvents){
        when(event){
            is TodoEvents.SetDescription -> {
                _state.update {
                    it.copy(
                        description = event.setDescription
                    )
                }
            }

            is TodoEvents.SetTitle -> {
                _state.update {
                    it.copy(
                        title = event.setTitle
                    )
                }
            }
            is TodoEvents.deleteTodo -> {
                viewModelScope.launch {
                    dao.deleteTodoList(event.todo)
                }
            }
            TodoEvents.saveTodo -> {
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

                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        isAddingTodo = false
                    )
                }
            }
            TodoEvents.hideDialog -> {
                _state.update {
                    it.copy(
                        isAddingTodo = false
                    )
                }
            }
            TodoEvents.showDialog -> {
                _state.update {
                    it.copy(
                        isAddingTodo = true
                    )
                }
            }
        }
    }
}