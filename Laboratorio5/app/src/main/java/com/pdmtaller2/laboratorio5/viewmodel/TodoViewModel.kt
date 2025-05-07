package com.pdmtaller2.laboratorio5.viewmodel

import androidx.lifecycle.ViewModel
import com.pdmtaller2.laboratorio5.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.Date

class TodoViewModel : ViewModel() {
    private val _taskList = MutableStateFlow<List<Task>>(emptyList())
    val taskList: StateFlow<List<Task>> = _taskList

    private val _openDialog = MutableStateFlow(false)
    val openDialog: StateFlow<Boolean> = _openDialog

    private val _openDateDialog = MutableStateFlow(false)
    val openDateDialog: StateFlow<Boolean> = _openDateDialog

    private val _newTask = MutableStateFlow(Task(id = -1, title = "", description = "", endDate = Date(), isCompleted = false))
    val newTask: StateFlow<Task> = _newTask

    fun showDialog(show: Boolean = true) {
        _openDialog.value = show
    }

    fun showDateDialog(show: Boolean = true) {
        _openDateDialog.value = show
    }

    fun updateTitle(title: String) {
        _newTask.value = _newTask.value.copy(title = title)
    }

    fun updateDescription(description: String) {
        _newTask.value = _newTask.value.copy(description = description)
    }

    fun updateNewTaskDate(date: Date) {
        _newTask.value = _newTask.value.copy(endDate = date)
    }

    fun addTask() {
        val nextId = (_taskList.value.maxOfOrNull { it.id } ?: 0) + 1
        _taskList.value = _taskList.value + _newTask.value.copy(id = nextId)
        _newTask.value = _newTask.value.copy(id = -1, title = "", description = "")
    }

    fun deleteTask(index: Int) {
        _taskList.value = _taskList.value.toMutableList().apply {
            removeAt(index)
        }
    }
    fun updateChecked(index: Int, isChecked: Boolean) {
        _taskList.value = _taskList.value.toMutableList().apply {
            this[index] = this[index].copy(isCompleted = isChecked)
        }
    }

}