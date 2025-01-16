package com.example.fixflow

data class Case(
    val id: String = "",
    val name: String = "",
    val createdAt: Long = System.currentTimeMillis(),  // Add timestamp
    val createdBy: String = ""  // Add user identifier
)
