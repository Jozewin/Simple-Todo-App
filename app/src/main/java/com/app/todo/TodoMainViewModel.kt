package com.app.todo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TodoMainViewModel(
    private val dao : TodoDAO
) : ViewModel() {

    private val _state = MutableStateFlow(TodoState())
    val state = _state

    init {
        viewModelScope.launch {
            dao.showAllTodos().collect{
                _state.value = state.value.copy(
                    todos = it
                )
            }
        }
        Log.d("Main", "The list ${state.value.todos}")
    }


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

                _state.update {
                    it.copy(
                        title = "",
                        description = "",
                        isAddingTodo = false
                    )
                }
                Log.d("Main", "The list ${state.value.todos}")

            }
            TodoEvents.HideDialog -> {
                _state.update {
                    it.copy(
                        isAddingTodo = false
                    )
                }
            }
            TodoEvents.ShowDialog -> {
                _state.update {
                    it.copy(
                        isAddingTodo = true
                    )
                }
            }

            is TodoEvents.UpdateTodoChecked -> {
                Log.d("Main", "The list in checkBox ${state.value.todos}")

                val todoChanged = event.todoDataIsChecked.copy(
                    isChecked = event.isFinished
                )

                viewModelScope.launch {
                    dao.updateTodoList(todoChanged)
                }
                Log.d("Main", "The list in after checkBox ${state.value.todos}")

            }
        }
    }
}