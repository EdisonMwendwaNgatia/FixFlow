package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class UserDashboardActivity : AppCompatActivity() {
    // Define rvCases as a class-level property
    private lateinit var rvCases: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        // Initialize rvCases
        rvCases = findViewById(R.id.rvCases)
        rvCases.layoutManager = LinearLayoutManager(this)

        // Initialize the adapter with an empty list initially
        rvCases.adapter = CaseAdapter(emptyList()) { case ->
            val intent = Intent(this, IssueActivity::class.java)
            intent.putExtra("caseId", case.id)
            startActivity(intent)
        }

        // Fetch and display the cases
        fetchCases()
    }

    private fun fetchCases() {
        FirebaseService.getCases { fetchedCases, error ->
            if (error == null) {
                fetchedCases?.let { cases ->
                    // Update the adapter with the fetched cases
                    (rvCases.adapter as CaseAdapter).apply {
                        (this as CaseAdapter).updateData(cases)
                    }
                }
            } else {
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
