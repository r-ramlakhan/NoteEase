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
import com.example.noteease.databinding.ActivitySignUpBinding
import com.example.noteease.viewmodel.AuthViewModel

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private val authViewModel: AuthViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
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

        binding.btnSignUp.setOnClickListener {
            val name = binding.etNameSignUp.text.toString()
            val email = binding.etEmailSignUp.text.toString()
            val password = binding.etPasswordSignUp.text.toString()
            val confirmPassword = binding.etConfirmPasswordSignUP.text.toString()

            if (name.isEmpty() && email.isEmpty() && password.isEmpty() && confirmPassword.isEmpty()) {
                binding.tiNameSignUp.error = "Name is required"
                binding.tiEmailSignUp.error = "Email is required"
                binding.tiPasswordSignUp.error = "Password is required"
                binding.tiConfirmPassword.error = "Password is required"
            }
            if (name.isEmpty()) {
                binding.tiNameSignUp.error = "Name is required"
            }
            if (email.isEmpty()) {
                binding.tiEmailSignUp.error = "Email is required"
            }
            if (password.isEmpty()) {
                binding.tiPasswordSignUp.error = "Password is required"
            }
            if (confirmPassword.isEmpty()) {
                binding.tiConfirmPassword.error = "Password is required"
            }
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                if (password == confirmPassword) {
                    authViewModel.signUp(name, email, password)
                } else {
                    binding.tiConfirmPassword.error = "Password didn't match"
                }
            }
        }

        binding.tvSignIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}