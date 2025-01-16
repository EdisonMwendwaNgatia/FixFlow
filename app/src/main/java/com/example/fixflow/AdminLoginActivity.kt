package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_login)

        val btnLogin: Button = findViewById(R.id.btnLogin)
        val etEmail: EditText = findViewById(R.id.etEmail)
        val etPassword: EditText = findViewById(R.id.etPassword)

        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            FirebaseService.loginUser(email, password) { success, message ->
                if (success) {
                    startActivity(Intent(this, AdminDashboardActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, "Login failed: $message", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
