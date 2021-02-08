package com.sisada.neversituptodo.viewmodel

import com.sisada.neversituptodo.model.Task
import com.sisada.neversituptodo.repository.Repository

class TaskViewModel {

    fun getAllTasks(token:String) = Repository.getAllTask(token)
    fun addTask(token:String,task: Task) = Repository.addTask(token,task)
    fun updateTask(token:String,task:Task) = Repository.updateTask(token,task)
    fun deleteTask(token:String,task:Task) = Repository.deleteTask(token,task)

}