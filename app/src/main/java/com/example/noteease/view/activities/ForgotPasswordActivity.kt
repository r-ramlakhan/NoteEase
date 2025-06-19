package com.example.noteease.view.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.noteease.R
import com.example.noteease.databinding.ActivityForgotPasswordBinding
import com.example.noteease.viewmodel.AuthViewModel

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgotPasswordBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        authViewModel.resetStatus.observe(this, Observer { succes ->
            if (succes) {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                finish()
            }
        })

        binding.btnSubmit.setOnClickListener {
            val email = binding.etEmailForgotPass.text.toString()
            if (email.isEmpty()) {
                binding.tiEmailForgotPass.error = "Email is required"
            }
            if (email.isNotEmpty()) {
                authViewModel.resetPassword(email)
            }
        }
    }
}