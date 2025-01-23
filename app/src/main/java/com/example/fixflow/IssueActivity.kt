package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class IssueActivity : AppCompatActivity() {
    private lateinit var rvIssues: RecyclerView // Class-level property for rvIssues
    private lateinit var caseId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue)

        // Initialize rvIssues
        rvIssues = findViewById(R.id.rvIssues)
        // Retrieve caseId from the intent
        caseId = intent.getStringExtra("caseId") ?: ""

        rvIssues.layoutManager = LinearLayoutManager(this)
        rvIssues.adapter = IssueAdapter(emptyList()) { issue ->
            // Handle issue item click
            val intent = Intent(this, SolutionActivity::class.java)
            intent.putExtra("issueId", issue.id)
            intent.putExtra("caseId", caseId)  // Pass the caseId here as well
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
                        updateData(issues)
                    }
                }
            } else {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
