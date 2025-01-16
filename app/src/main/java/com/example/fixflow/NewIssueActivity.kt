// NewIssueActivity.kt
package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class NewIssueActivity : AppCompatActivity() {

    private lateinit var etIssueName: EditText
    private lateinit var btnSaveIssue: Button
    private var caseId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_issue)

        etIssueName = findViewById(R.id.etIssueName)
        btnSaveIssue = findViewById(R.id.btnSaveIssue)

        // Get the caseId passed from CaseIssuesActivity
        caseId = intent.getStringExtra("caseId")

        btnSaveIssue.setOnClickListener {
            val issueName = etIssueName.text.toString().trim()
            if (issueName.isNotEmpty()) {
                // Add the new issue to Firebase
                FirebaseService.addIssue(caseId!!, issueName) { success, issueId ->
                    if (success) {
                        Toast.makeText(this, "Issue added", Toast.LENGTH_SHORT).show()

                        // After saving the issue, open NewChecklistActivity
                        val intent = Intent(this, NewChecklistActivity::class.java)
                        intent.putExtra("caseId", caseId)
                        intent.putExtra("issueId", issueId)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Failed to add issue", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a valid issue name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
