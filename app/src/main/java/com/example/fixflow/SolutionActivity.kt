package com.example.fixflow

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SolutionActivity : AppCompatActivity() {
    private lateinit var rvChecklist: RecyclerView
    private lateinit var issueId: String
    private lateinit var caseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_solution)

        // Retrieve intent extras
        caseId = intent.getStringExtra("caseId") ?: ""
        issueId = intent.getStringExtra("issueId") ?: ""

        if (caseId.isEmpty() || issueId.isEmpty()) {
            Toast.makeText(this, "Invalid case or issue ID", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Initialize RecyclerView
        rvChecklist = findViewById(R.id.rvChecklist)
        rvChecklist.layoutManager = LinearLayoutManager(this)
        rvChecklist.adapter = ChecklistAdapter(emptyList()) // Set an empty list initially

        // Fetch checklist steps
        fetchChecklistSteps()
    }

    private fun fetchChecklistSteps() {
        FirebaseService.getChecklist(caseId, issueId) { fetchedSteps, error ->
            if (error == null) {
                fetchedSteps?.let { steps ->
                    if (steps.isNotEmpty()) {
                        Log.d("SolutionActivity", "Checklist steps fetched: $steps")
                        // Update RecyclerView with the checklist
                        (rvChecklist.adapter as ChecklistAdapter).updateData(steps)
                    } else {
                        Toast.makeText(this, "No checklist steps for this issue.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Log.e("SolutionActivity", "Error fetching checklist: $error")
                Toast.makeText(this, "Error fetching checklist: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
