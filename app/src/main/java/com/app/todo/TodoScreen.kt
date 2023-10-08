package com.app.todo

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(
    state: TodoState,
    onEvents: (TodoEvents) -> Unit
) {

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        "Todo App", fontWeight = FontWeight.SemiBold,
                        fontSize = 32.sp
                    )
                })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onEvents(TodoEvents.ShowDialog)
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Todo")
            }
        },
        modifier = Modifier

    ) { padding ->

        if (state.isAddingTodo) {
            AddTodoDialog(state = state, onEvents = onEvents)
        }
        LazyColumn(
            contentPadding = padding,
            modifier = Modifier
                .fillMaxSize()
        ) {
            items(state.todos) { todo ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 6.dp, vertical = 6.dp)


                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp)
                    ) {
                        Checkbox(
                            checked = todo.isChecked,
                            onCheckedChange =
                            {
                                onEvents(TodoEvents.UpdateTodoChecked(it, todo))
                            }
                        )
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = todo.title,
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Medium,
                            )

                            Spacer(modifier = Modifier.height(4.dp))
                            Text(text = todo.description)
                        }

                        IconButton(
                            onClick = {
                                onEvents(TodoEvents.DeleteTodo(todo))
                            })
                        {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = null)
                        }
                    }

                }
            }
        }
    }
}

@Composable
fun AddTodoDialog(
    state: TodoState,
    onEvents: (TodoEvents) -> Unit
) {
    AlertDialog(
        onDismissRequest = {
            onEvents(TodoEvents.HideDialog)
        },
        confirmButton = {
            onEvents(TodoEvents.ShowDialog)
        },

        title = {
            Text(
                text = "Add Todo",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

        },

        text = {
            Column(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                TextField(
                    value = state.title,
                    onValueChange = {
                        onEvents(TodoEvents.SetTitle(it))
                    },
                    placeholder = {
                        Text(text = "Title")
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    maxLines = 1
                )
                TextField(
                    value = state.description,
                    onValueChange = {
                        onEvents(TodoEvents.SetDescription(it))
                    },
                    placeholder = {
                        Text(text = "Description")
                    },
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )

                Box(modifier = Modifier.fillMaxWidth()) {
                    Button(
                        onClick = {
                            onEvents(TodoEvents.SaveTodo)
                        },
                        modifier = Modifier.align(Alignment.CenterEnd)
                    ) {
                        Text(text = "Save")
                    }
                }
            }
        }

    )
}