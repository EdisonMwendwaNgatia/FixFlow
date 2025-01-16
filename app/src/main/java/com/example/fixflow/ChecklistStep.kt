package com.example.fixflow

data class ChecklistStep(
    val id: String,
    val isCompleted: Boolean = false,
    val description: String = "",
    val createdAt: Long = System.currentTimeMillis(),  // Add timestamp
    val createdBy: String = ""  // Add user identifier
)
