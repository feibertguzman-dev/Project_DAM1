package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.User
import com.example.data.repository.UserRepository
import com.example.util.AuthHelper
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface AuthState {
    object Loading : AuthState
    object Unauthenticated : AuthState
    data class Authenticated(val user: FirebaseUser, val profile: User) : AuthState
    data class NeedsRole(val user: FirebaseUser) : AuthState
    data class Error(val message: String) : AuthState
}

class AuthViewModel(private val repository: UserRepository) : ViewModel() {
    private val _state = MutableStateFlow<AuthState>(AuthState.Loading)
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            AuthHelper.authStateFlow().collect { firebaseUser ->
                if (firebaseUser != null) {
                    val profile = repository.getUser(firebaseUser.uid)
                    if (profile != null) {
                        _state.value = AuthState.Authenticated(firebaseUser, profile)
                    } else {
                        _state.value = AuthState.NeedsRole(firebaseUser)
                    }
                } else {
                    _state.value = AuthState.Unauthenticated
                }
            }
        }
    }

    fun selectRole(role: String) {
        val currentState = _state.value
        if (currentState is AuthState.NeedsRole) {
            viewModelScope.launch {
                val newProfile = User(
                    id = currentState.user.uid,
                    email = currentState.user.email ?: "",
                    name = currentState.user.displayName ?: "Usuario",
                    role = role
                )
                repository.saveUser(newProfile)
                _state.value = AuthState.Authenticated(currentState.user, newProfile)
            }
        }
    }

    fun updateRole(role: String) {
        val currentState = _state.value
        if (currentState is AuthState.Authenticated) {
            viewModelScope.launch {
                val updatedProfile = currentState.profile.copy(role = role)
                repository.saveUser(updatedProfile)
                _state.value = currentState.copy(profile = updatedProfile)
            }
        }
    }
}
