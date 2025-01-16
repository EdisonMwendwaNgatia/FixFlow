package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class IssueActivity : AppCompatActivity() {
    private lateinit var rvIssues: RecyclerView // Class-level property for rvIssues
    private lateinit var caseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        // Initialize rvIssues
        rvIssues = findViewById(R.id.rvIssues)
        caseId = intent.getStringExtra("caseId") ?: ""

        rvIssues.layoutManager = LinearLayoutManager(this)
        rvIssues.adapter = IssueAdapter(emptyList()) { issue ->
            val intent = Intent(this, SolutionActivity::class.java)
            intent.putExtra("issueId", issue.id)
            startActivity(intent)
        }

        // Fetch and display the issues
        fetchIssues()
    }

    private fun fetchIssues() {
        FirebaseService.getIssues(caseId) { fetchedIssues, error ->
            if (error == null) {
                fetchedIssues?.let { issues ->
                    // Update the adapter with the fetched issues
                    (rvIssues.adapter as IssueAdapter).apply {
                        (this as IssueAdapter).updateData(issues)
                    }
                }
            } else {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
