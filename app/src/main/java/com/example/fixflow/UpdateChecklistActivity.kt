package com.example.fixflow

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class UpdateChecklistActivity : AppCompatActivity() {

    private lateinit var etChecklistStep: EditText
    private lateinit var btnUpdateChecklist: Button
    private lateinit var caseId: String
    private lateinit var issueId: String
    private lateinit var checklistStepId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_checklist)

        etChecklistStep = findViewById(R.id.etChecklistStep)
        btnUpdateChecklist = findViewById(R.id.btnUpdateChecklist)

        // Get data from the intent
        caseId = intent.getStringExtra("caseId") ?: ""
        issueId = intent.getStringExtra("issueId") ?: ""
        checklistStepId = intent.getStringExtra("checklistStepId") ?: ""

        // Populate the EditText with the current checklist step
        etChecklistStep.setText(intent.getStringExtra("checklistStep"))

        btnUpdateChecklist.setOnClickListener {
            val updatedStep = etChecklistStep.text.toString()
            if (updatedStep.isNotEmpty()) {
                FirebaseService.updateChecklistStep(caseId, issueId, checklistStepId, updatedStep) { success ->
                    if (success) {
                        Toast.makeText(this, "Checklist step updated", Toast.LENGTH_SHORT).show()
                        finish() // Return to ChecklistActivity
                    } else {
                        Toast.makeText(this, "Failed to update checklist step", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a checklist step", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
