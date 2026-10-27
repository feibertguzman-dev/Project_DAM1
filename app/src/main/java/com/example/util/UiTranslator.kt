package com.example.util

import android.content.Context
import com.example.R

object UiTranslator {
    fun translateStatus(context: Context, status: String): String {
        return when (status) {
            "UPCOMING" -> context.getString(R.string.status_upcoming)
            "LIVE" -> context.getString(R.string.status_live)
            "COMPLETED" -> context.getString(R.string.status_completed)
            "REGISTERED" -> context.getString(R.string.status_registered)
            "EVIDENCE_SUBMITTED" -> context.getString(R.string.status_evidence_submitted)
            "GRADED" -> context.getString(R.string.status_graded)
            else -> status
        }
    }

    fun translateRole(context: Context, role: String): String {
        return when (role) {
            "STUDENT" -> context.getString(R.string.role_student)
            "TEACHER" -> context.getString(R.string.role_teacher)
            "ADMIN" -> context.getString(R.string.role_admin)
            else -> role
        }
    }
}
