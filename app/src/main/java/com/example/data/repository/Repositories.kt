package com.example.data.repository

import android.content.Context
import com.example.R
import com.example.data.model.Attendance
import com.example.data.model.Catedra
import com.example.data.model.User
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.tasks.await

class CatedraRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(context.applicationContext.getString(R.string.firestore_database_id))
    )

    fun getCatedras(): Flow<List<Catedra>> {
        return db.collection("catedras")
            .orderBy("createdAt")
            .snapshots()
            .map { it.toObjects(Catedra::class.java) }
    }

    suspend fun saveCatedra(catedra: Catedra) {
        val ref = if (catedra.id.isEmpty()) {
            db.collection("catedras").document()
        } else {
            db.collection("catedras").document(catedra.id)
        }
        val data = catedra.copy(id = ref.id).toMap().toMutableMap()
        if (catedra.id.isEmpty()) {
            data["createdAt"] = FieldValue.serverTimestamp()
        }
        ref.set(data).await()
    }

    suspend fun deleteCatedra(id: String) {
        db.collection("catedras").document(id).delete().await()
    }
}

class AttendanceRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(context.applicationContext.getString(R.string.firestore_database_id))
    )

    fun getAttendancesByStudent(studentId: String): Flow<List<Attendance>> {
        return db.collection("attendances")
            .whereEqualTo("studentId", studentId)
            .snapshots()
            .map { it.toObjects(Attendance::class.java) }
    }

    fun getAttendancesByTeacher(teacherId: String): Flow<List<Attendance>> {
        return db.collection("attendances")
            .whereEqualTo("teacherId", teacherId)
            .snapshots()
            .map { it.toObjects(Attendance::class.java) }
    }

    fun getAllAttendances(): Flow<List<Attendance>> {
        return db.collection("attendances")
            .snapshots()
            .map { it.toObjects(Attendance::class.java) }
    }

    suspend fun registerAttendance(attendance: Attendance) {
        val ref = db.collection("attendances").document()
        val data = attendance.copy(id = ref.id).toMap().toMutableMap()
        data["timestamp"] = FieldValue.serverTimestamp()
        ref.set(data).await()
    }

    suspend fun updateAttendance(attendance: Attendance) {
        db.collection("attendances").document(attendance.id)
            .set(attendance.toMap())
            .await()
    }
}

class UserRepository(private val db: FirebaseFirestore) {
    constructor(context: Context) : this(
        FirebaseFirestore.getInstance(context.applicationContext.getString(R.string.firestore_database_id))
    )

    suspend fun getUser(id: String): User? {
        return db.collection("users").document(id).get().await().toObject(User::class.java)
    }

    suspend fun saveUser(user: User) {
        val data = user.toMap().toMutableMap()
        if (user.createdAt == null) {
            data["createdAt"] = FieldValue.serverTimestamp()
        }
        db.collection("users").document(user.id).set(data).await()
    }

    fun getAllTeachers(): Flow<List<User>> {
        return db.collection("users")
            .whereEqualTo("role", "TEACHER")
            .snapshots()
            .map { it.toObjects(User::class.java) }
    }
}
