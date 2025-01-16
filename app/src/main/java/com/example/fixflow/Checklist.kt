package com.example.fixflow

data class Checklist(
    val id: String,
    val issueId: String,
    val steps: List<ChecklistStep>
)
