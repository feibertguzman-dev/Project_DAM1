package com.example.data.model

import com.google.firebase.Timestamp

data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "STUDENT",
    val courseInfo: String? = null,
    val createdAt: Timestamp? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "email" to email,
            "name" to name,
            "role" to role,
            "courseInfo" to courseInfo,
            "createdAt" to createdAt
        ).filterValues { it != null }
    }
}

data class Catedra(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val streamUrl: String? = null,
    val videoUrl: String? = null,
    val aiSummary: String? = null,
    val status: String = "UPCOMING",
    val createdAt: Timestamp? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "title" to title,
            "description" to description,
            "date" to date,
            "time" to time,
            "streamUrl" to streamUrl,
            "videoUrl" to videoUrl,
            "aiSummary" to aiSummary,
            "status" to status,
            "createdAt" to createdAt
        ).filterValues { it != null }
    }
}

data class Attendance(
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val catedraId: String = "",
    val teacherId: String = "",
    val teacherName: String = "",
    val evidenceUrl: String? = null,
    val grade: Double? = null,
    val status: String = "REGISTERED",
    val timestamp: Timestamp? = null
) {
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "studentId" to studentId,
            "studentName" to studentName,
            "catedraId" to catedraId,
            "teacherId" to teacherId,
            "teacherName" to teacherName,
            "evidenceUrl" to evidenceUrl,
            "grade" to grade,
            "status" to status,
            "timestamp" to timestamp
        ).filterValues { it != null }
    }
}
