package com.sisada.neversituptodo.model

data class GetAllTaskResponse(
    val count: Int,
    val `data`: List<Task>
)