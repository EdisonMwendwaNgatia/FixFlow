package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CaseIssuesActivity : AppCompatActivity() {

    private lateinit var rvIssues: RecyclerView
    private lateinit var btnAddIssue: Button
    private lateinit var caseId: String
    private lateinit var caseName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_issues)

        rvIssues = findViewById(R.id.rvIssues)
        btnAddIssue = findViewById(R.id.btnAddIssue)

        // Get case details from the intent
        caseId = intent.getStringExtra("caseId") ?: ""
        caseName = intent.getStringExtra("caseName") ?: ""

        rvIssues.layoutManager = LinearLayoutManager(this)

        // Fetch issues related to the selected case from Firebase
        FirebaseService.getIssues(caseId) { issues, error ->
            if (error == null) {
                // Set up the adapter with fetched issues
                rvIssues.adapter = IssueAdapter(issues ?: emptyList()) { issue ->
                    // Navigate to ChecklistActivity when an issue is clicked
                    val intent = Intent(this, ChecklistActivity::class.java)
                    intent.putExtra("caseId", caseId)
                    intent.putExtra("issueId", issue.id)
                    startActivity(intent)
                }
            } else {
                // Handle error (e.g., show a toast)
                Toast.makeText(this, "Error fetching issues: $error", Toast.LENGTH_SHORT).show()
            }
        }

        // Add new issue
        btnAddIssue.setOnClickListener {
            val intent = Intent(this, NewIssueActivity::class.java)
            intent.putExtra("caseId", caseId)  // Pass the caseId to the next activity
            startActivity(intent)
        }
    }
}
