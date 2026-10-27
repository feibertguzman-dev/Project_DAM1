package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.repository.UserRepository
import com.example.ui.screens.LoginScreen
import androidx.compose.ui.unit.dp
import com.example.ui.screens.MainScreen
import com.example.ui.screens.RoleSelectionScreen
import com.example.ui.theme.Theme
import com.example.ui.viewmodel.AuthState
import com.example.ui.viewmodel.AuthViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Theme {
                val userRepo = UserRepository(this)
                val authViewModel: AuthViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T = AuthViewModel(userRepo) as T
                })
                
                val state by authViewModel.state.collectAsState()
                
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    when (val currentState = state) {
                        is AuthState.Loading -> {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator()
                            }
                        }
                        is AuthState.Unauthenticated -> LoginScreen(authViewModel)
                        is AuthState.NeedsRole -> RoleSelectionScreen { authViewModel.selectRole(it) }
                        is AuthState.Authenticated -> MainScreen(currentState)
                        is AuthState.Error -> {
                            // Error screen
                        }
                    }
                }
            }
        }
    }
}
