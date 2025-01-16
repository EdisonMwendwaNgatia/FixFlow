package com.example.fixflow

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddChecklistActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_checklist)

        val btnSubmitChecklist: Button = findViewById(R.id.btnSubmitChecklist)
        val etChecklistStep: EditText = findViewById(R.id.etChecklistStep)

        val caseId = intent.getStringExtra("caseId")
        val issueId = intent.getStringExtra("issueId")

        btnSubmitChecklist.setOnClickListener {
            val checklistStep = etChecklistStep.text.toString()

            if (caseId != null && issueId != null && checklistStep.isNotEmpty()) {
                FirebaseService.addChecklistStep(caseId, issueId, checklistStep) { success ->
                    if (success) {
                        Toast.makeText(this, "Checklist step added successfully", Toast.LENGTH_SHORT).show()
                        etChecklistStep.text.clear()
                    } else {
                        Toast.makeText(this, "Failed to add checklist step", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a checklist step", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
