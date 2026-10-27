package com.example.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.repository.AttendanceRepository
import com.example.data.repository.CatedraRepository
import com.example.data.repository.UserRepository
import com.example.ui.viewmodel.*
import com.example.util.AuthHelper
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(authState: AuthState.Authenticated) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    
    // Repositories
    val catedraRepo = remember { CatedraRepository(context) }
    val attendanceRepo = remember { AttendanceRepository(context) }
    val userRepo = remember { UserRepository(context) }
    
    // ViewModels
    val catedraVM: CatedraViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T = CatedraViewModel(catedraRepo) as T
    })
    val attendanceVM: AttendanceViewModel = viewModel(factory = object : androidx.lifecycle.ViewModelProvider.Factory {
        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T = AttendanceViewModel(attendanceRepo, userRepo) as T
    })
    
    val profile = authState.profile

    Scaffold(
        bottomBar = {
            if (profile.role == "ADMIN") {
                // Simplified: Admin just stays in AdminDashboard for now
            }
        },
        floatingActionButton = {
            // Logout button
            SmallFloatingActionButton(
                onClick = { AuthHelper.signOut(context, scope, {}) },
                modifier = Modifier.padding(bottom = 80.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
            }
        }
    ) { padding ->
        Surface(modifier = Modifier.padding(padding)) {
            val translatedRole = UiTranslator.translateRole(context, profile.role)
            when (profile.role) {
                "ADMIN" -> AdminDashboard(catedraVM, profile)
                "TEACHER" -> TeacherDashboard(attendanceVM, profile)
                else -> StudentDashboard(catedraVM, attendanceVM, profile)
            }
        }
    }
}
