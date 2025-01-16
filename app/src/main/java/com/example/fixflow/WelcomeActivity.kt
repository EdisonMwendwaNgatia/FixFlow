package com.example.fixflow

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val btnUserLogin:Button = findViewById(R.id.btnUserLogin)
        val btnAdminLogin:Button = findViewById(R.id.btnAdminLogin)

        btnUserLogin.setOnClickListener {
            startActivity(Intent(this, UserLoginActivity::class.java))
        }

        btnAdminLogin.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
