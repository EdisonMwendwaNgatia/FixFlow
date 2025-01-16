package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class AddCasesActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_cases)

        val btnSubmitCase: Button = findViewById(R.id.btnSubmitCase)
        val etCaseName: EditText = findViewById(R.id.etCaseName)

        // Get the current user ID or email
        val currentUser = FirebaseAuth.getInstance().currentUser
        val createdBy = currentUser?.email ?: "Unknown User"

        btnSubmitCase.setOnClickListener {
            val caseName = etCaseName.text.toString()

            if (caseName.isNotEmpty()) {
                FirebaseService.addCase(caseName, createdBy) { success, caseId ->
                    if (success && caseId != null) {
                        Toast.makeText(this, "Case added successfully", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, AddIssueActivity::class.java)
                        intent.putExtra("caseId", caseId)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to add case", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter a case name", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
