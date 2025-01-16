package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AdminDashboardActivity : AppCompatActivity() {

    private lateinit var rvCases: RecyclerView
    private lateinit var btnAddCase: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_dashboard)

        rvCases = findViewById(R.id.rvCases)
        btnAddCase = findViewById(R.id.btnAddCase)

        rvCases.layoutManager = LinearLayoutManager(this)

        // Fetch cases from Firebase
        FirebaseService.getCases { cases, error ->
            if (error == null) {
                // Set up the adapter with fetched cases
                rvCases.adapter = AdminCaseAdapter(cases ?: emptyList()) { case ->
                    // Navigate to CaseIssuesActivity when a case is clicked
                    val intent = Intent(this, CaseIssuesActivity::class.java)
                    intent.putExtra("caseId", case.id)
                    intent.putExtra("caseName", case.name)
                    startActivity(intent)
                }
            } else {
                // Handle error (e.g., show a toast)
                Toast.makeText(this, "Error fetching cases: $error", Toast.LENGTH_SHORT).show()
            }
        }

        // Add a new case when the button is clicked
        btnAddCase.setOnClickListener {
            startActivity(Intent(this, AddCasesActivity::class.java))
        }
    }
}
