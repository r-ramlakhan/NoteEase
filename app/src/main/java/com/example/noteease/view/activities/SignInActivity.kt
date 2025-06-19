package com.example.noteease.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import com.example.noteease.R
import com.example.noteease.databinding.ActivitySignInBinding
import com.example.noteease.viewmodel.AuthViewModel

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        authViewModel.checkUser()
        authViewModel.user.observe(this, Observer { user ->
            binding.progressBar.visibility = View.VISIBLE
            if (user != null) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                binding.progressBar.visibility = View.INVISIBLE
            }
        })

        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmailSignIn.text.toString()
            val password = binding.etPasswordSignIn.text.toString()

            if (email.isEmpty() && password.isEmpty()) {
                binding.tiEmailSignIn.error = "Email is required"
                binding.tiPasswordSignIn.error = "Password is required"
            }
            if (email.isEmpty()) {
                binding.tiEmailSignIn.error = "Email is required"
            }
            if (password.isEmpty()) {
                binding.tiPasswordSignIn.error = "Password is required"
            }

            if (email.isNotEmpty() && password.isNotEmpty()) {
                authViewModel.signIn(email, password)
            }
        }

        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.tvForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}