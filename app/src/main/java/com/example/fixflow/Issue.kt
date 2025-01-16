package com.example.fixflow

data class Issue(
    val id: String = "",
    val name: String = "",
    val description: String = "",  // Add description
    val title: String = "",  // Add title
    val createdAt: Long = System.currentTimeMillis(),  // Add timestamp
    val createdBy: String = ""  // Add user identifier
)
