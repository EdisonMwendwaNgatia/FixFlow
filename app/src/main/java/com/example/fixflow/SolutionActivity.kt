package com.example.fixflow

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SolutionActivity : AppCompatActivity() {
    private lateinit var rvChecklist: RecyclerView // Class-level property for rvChecklist
    private lateinit var issueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)

        // Initialize rvChecklist
        rvChecklist = findViewById(R.id.rvChecklist)
        issueId = intent.getStringExtra("issueId") ?: ""

        rvChecklist.layoutManager = LinearLayoutManager(this)
        rvChecklist.adapter = ChecklistAdapter(emptyList()) // Start with an empty list

        // Fetch and display the checklist
        fetchChecklist()
    }

    private fun fetchChecklist() {
        val caseId = intent.getStringExtra("caseId") ?: ""
        FirebaseService.getChecklist(caseId, issueId) { fetchedSteps, error ->
            if (error == null) {
                fetchedSteps?.let { steps ->
                    // Update the adapter with the fetched checklist steps
                    (rvChecklist.adapter as ChecklistAdapter).apply {
                        (this as ChecklistAdapter).updateData(steps)
                    }
                }
            } else {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
