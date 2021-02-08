package com.sisada.neversituptodo.model

data class UpdateTaskResponse(
    val count: Int,
    val `data`: List<Task>
)