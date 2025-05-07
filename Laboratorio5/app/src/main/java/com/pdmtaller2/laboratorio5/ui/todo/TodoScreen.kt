package com.pdmtaller2.laboratorio5.ui.todo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.getValue
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavHostController
import com.pdmtaller2.laboratorio5.model.Task
import com.pdmtaller2.laboratorio5.navigation.ScreenRoutes
import com.pdmtaller2.laboratorio5.ui.todo.components.Cards
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.pdmtaller2.laboratorio5.viewmodel.TodoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreen(navController: NavHostController, viewModel: TodoViewModel) {
    val taskList by viewModel.taskList.collectAsState()
    val openDialog by viewModel.openDialog.collectAsState()
    val openDateDialog by viewModel.openDateDialog.collectAsState()
    val newTask by viewModel.newTask.collectAsState()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { viewModel.showDialog() }) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Button(onClick = { navController.navigate(ScreenRoutes.home) }) {
                    Text("Home")
                }
                Button(onClick = { navController.navigate(ScreenRoutes.sensor) }) {
                    Text("Sensores")
                }
            }
            LazyColumn(modifier = Modifier.padding(16.dp)) {
                itemsIndexed(taskList) { index, task ->
                    Cards(
                        pos = index,
                        title = task.title,
                        description = task.description,
                        endDate = task.endDate,
                        checked = task.isCompleted,
                        delete = { viewModel.deleteTask(it) },
                        check = { isChecked, pos -> viewModel.updateChecked(pos, isChecked) }
                    )
                    Spacer(modifier = Modifier.padding(8.dp))
                }
            }
        }
        if (openDialog) {
            TaskInputDialog(
                newTask = newTask,
                onTitleChange = { viewModel.updateTitle(it) },
                onDescriptionChange = { viewModel.updateDescription(it) },
                onDismiss = { viewModel.showDialog(false) },
                onAdd = {
                    viewModel.addTask()
                    viewModel.showDialog(false)
                },
                onDateRequest = { viewModel.showDateDialog(true) }
            )
        }
        if (openDateDialog) {
            DatePickerDialogWrapper(
                onDismiss = { viewModel.showDateDialog(false) },
                onDateSelected = { viewModel.updateNewTaskDate(it) }
            )
        }
    }
}

@Composable
fun TaskInputDialog(
    newTask: Task,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onAdd: () -> Unit,
    onDateRequest: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = onAdd) {
                Text("Agregar")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        title = { Text("Nueva tarea") },
        text = {
            Column {
                TextField(
                    value = newTask.title,
                    onValueChange = onTitleChange,
                    label = { Text("Título") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = newTask.description,
                    onValueChange = onDescriptionChange,
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(newTask.endDate),
                    onValueChange = {},
                    label = { Text("Fecha de entrega") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = onDateRequest) {
                    Text("Seleccionar Fecha")
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerDialogWrapper(
    onDismiss: () -> Unit,
    onDateSelected: (Date) -> Unit
) {
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = Date().time)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            Button(onClick = {
                val selectedDate = datePickerState.selectedDateMillis?.let { Date(it) }
                if (selectedDate != null) onDateSelected(selectedDate)
                onDismiss()
            }) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancelar")
            }
        },
        properties = DialogProperties(
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        DatePicker(state = datePickerState)
    }
}