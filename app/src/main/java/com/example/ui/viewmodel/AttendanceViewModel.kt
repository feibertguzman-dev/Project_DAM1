package com.example.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.model.Attendance
import com.example.data.model.User
import com.example.data.repository.AttendanceRepository
import com.example.data.repository.UserRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AttendanceViewModel(
    private val attendanceRepository: AttendanceRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    
    val teachers: StateFlow<List<User>> = userRepository.getAllTeachers()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getStudentAttendances(studentId: String): StateFlow<List<Attendance>> = 
        attendanceRepository.getAttendancesByStudent(studentId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getTeacherAttendances(teacherId: String): StateFlow<List<Attendance>> = 
        attendanceRepository.getAttendancesByTeacher(teacherId)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getAllAttendances(): StateFlow<List<Attendance>> = 
        attendanceRepository.getAllAttendances()
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun register(attendance: Attendance) {
        viewModelScope.launch {
            attendanceRepository.registerAttendance(attendance)
        }
    }

    fun submitEvidence(id: String, url: String) {
        viewModelScope.launch {
            val all = attendanceRepository.getAllAttendances() // Simplified for now
            // In a real app we'd fetch by ID directly
            // For now we'll use update with a partial match if we have the full object
        }
    }

    fun updateAttendance(attendance: Attendance) {
        viewModelScope.launch {
            attendanceRepository.updateAttendance(attendance)
        }
    }
}
