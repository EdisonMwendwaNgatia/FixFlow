package com.example.fixflow

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewChecklistActivity : AppCompatActivity() {

    private lateinit var etChecklistStep: EditText
    private lateinit var btnSaveChecklist: Button
    private var caseId: String? = null
    private var issueId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_checklist)

        etChecklistStep = findViewById(R.id.etChecklistStep)
        btnSaveChecklist = findViewById(R.id.btnSaveChecklist)

        // Get the caseId and issueId passed from the previous activity
        caseId = intent.getStringExtra("caseId")
        issueId = intent.getStringExtra("issueId")

        btnSaveChecklist.setOnClickListener {
            val checklistStep = etChecklistStep.text.toString().trim()

            if (caseId != null && issueId != null && checklistStep.isNotEmpty()) {
                FirebaseService.addChecklistStep(caseId!!, issueId!!, checklistStep) { success ->
                    if (success) {
                        Toast.makeText(this, "Checklist step added successfully", Toast.LENGTH_SHORT).show()
                        etChecklistStep.text.clear() // Clear the input field for the next entry
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
