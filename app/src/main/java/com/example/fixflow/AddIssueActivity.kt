package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AddIssueActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_issue)

        val btnSubmitIssue: Button = findViewById(R.id.btnSubmitIssue)
        val etIssueName: EditText = findViewById(R.id.etIssueName)

        val caseId = intent.getStringExtra("caseId")

        btnSubmitIssue.setOnClickListener {
            val issueName = etIssueName.text.toString()

            if (caseId != null && issueName.isNotEmpty()) {
                FirebaseService.addIssue(caseId, issueName) { success, issueId ->
                    if (success && issueId != null) {
                        Toast.makeText(this, "Issue added successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, AddChecklistActivity::class.java)
                        intent.putExtra("caseId", caseId)
                        intent.putExtra("issueId", issueId)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to add issue", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter an issue name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
