package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CaseIssuesActivity : AppCompatActivity() {

    private lateinit var rvIssues: RecyclerView
    private lateinit var btnAddIssue: Button
    private lateinit var caseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_case_issues)

        // Initialize views
        rvIssues = findViewById(R.id.rvIssues)
        btnAddIssue = findViewById(R.id.btnAddIssue)

        // Retrieve caseId from the intent
        caseId = intent.getStringExtra("caseId") ?: ""

        // Set up RecyclerView
        rvIssues.layoutManager = LinearLayoutManager(this)
        rvIssues.adapter = IssueAdapter(emptyList()) { issue ->
            // Handle issue item click
            val intent = Intent(this, ChecklistActivity::class.java)
            intent.putExtra("issueId", issue.id)
            intent.putExtra("caseId", caseId)
            startActivity(intent)
        }

        // Fetch and display issues related to the selected case
        fetchIssues()

        // Handle "Add Issue" button click (always visible for admin)
        btnAddIssue.setOnClickListener {
            val intent = Intent(this, NewIssueActivity::class.java)
            intent.putExtra("caseId", caseId) // Pass caseId for adding a new issue
            startActivity(intent)
        }
    }

    private fun fetchIssues() {
        FirebaseService.getIssues(caseId) { fetchedIssues, error ->
            if (error == null) {
                fetchedIssues?.let { issues ->
                    // Update the adapter with the fetched issues
                    (rvIssues.adapter as IssueAdapter).apply {
                        updateData(issues)
                    }
                }
            } else {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
