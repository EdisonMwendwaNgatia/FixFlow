package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ChecklistActivity : AppCompatActivity() {

    private lateinit var etChecklistStep: EditText
    private lateinit var btnSaveChecklist: Button
    private lateinit var rvChecklist: RecyclerView
    private lateinit var caseId: String
    private lateinit var issueId: String
    private val checklistAdapter = ChecklistAdapter(emptyList()) // Create an adapter without the click listener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_checklist)

        etChecklistStep = findViewById(R.id.etChecklistStep)
        btnSaveChecklist = findViewById(R.id.btnSaveChecklist)
        rvChecklist = findViewById(R.id.rvChecklist)

        // Get case and issue details from the intent
        caseId = intent.getStringExtra("caseId") ?: ""
        issueId = intent.getStringExtra("issueId") ?: ""

        // Set up RecyclerView
        rvChecklist.layoutManager = LinearLayoutManager(this)
        rvChecklist.adapter = checklistAdapter

        // Set item click listener to navigate to UpdateChecklistActivity
        checklistAdapter.setOnItemClickListener { checklistStepId, checklistStepDescription ->
            val intent = Intent(this, UpdateChecklistActivity::class.java).apply {
                putExtra("caseId", caseId)
                putExtra("issueId", issueId)
                putExtra("checklistStepId", checklistStepId) // Pass the step ID
                putExtra("checklistStep", checklistStepDescription) // Pass the description
            }
            startActivity(intent)
        }



        // Load the checklist for the issue
        fetchChecklist()

        btnSaveChecklist.setOnClickListener {
            val checklistStep = etChecklistStep.text.toString()
            if (checklistStep.isNotEmpty()) {
                FirebaseService.addChecklistStep(caseId, issueId, checklistStep) { success ->
                    if (success) {
                        Toast.makeText(this, "Checklist step added", Toast.LENGTH_SHORT).show()
                        etChecklistStep.text.clear()
                        fetchChecklist() // Refresh the checklist
                    } else {
                        Toast.makeText(this, "Failed to add checklist step", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a checklist step", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchChecklist() {
        FirebaseService.getChecklist(caseId, issueId) { checklistSteps, error ->
            if (error == null && checklistSteps != null) {
                checklistAdapter.updateData(checklistSteps)
            } else {
                Toast.makeText(this, error ?: "Error loading checklist", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
