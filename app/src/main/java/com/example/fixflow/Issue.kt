package com.example.fixflow

data class Issue(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val createdAt: Long = 0,
    val createdBy: String = "",
    val checklistSteps: List<String> = emptyList()
)


