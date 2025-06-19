package com.example.noteease.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteease.model.repositories.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.launch

class AuthViewModel: ViewModel() {
    private val authRepository = AuthRepository()
    private val _user = MutableLiveData<FirebaseUser?>()
    val user: LiveData<FirebaseUser?> get() = _user

    fun signUp(name: String, email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signUp(name, email, password)
            _user.value = result
        }
    }

    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            val result = authRepository.signIn(email, password)
            _user.value = result
        }
    }

    fun checkUser() {
        _user.value = authRepository.getCurrentUser()
    }
}