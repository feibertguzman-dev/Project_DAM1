# Documentación de API

## Modelos de Datos

### User
```kotlin
data class User(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val role: String = "STUDENT", // STUDENT | TEACHER | ADMIN
    val courseInfo: String? = null,
    val createdAt: Timestamp? = null
)
```

### Catedra
```kotlin
data class Catedra(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val date: String = "",
    val time: String = "",
    val streamUrl: String? = null,
    val videoUrl: String? = null,
    val aiSummary: String? = null,
    val status: String = "UPCOMING", // UPCOMING | LIVE | COMPLETED
    val createdAt: Timestamp? = null
)
```

### Attendance
```kotlin
data class Attendance(
    val id: String = "",
    val studentId: String = "",
    val studentName: String = "",
    val catedraId: String = "",
    val teacherId: String = "",
    val teacherName: String = "",
    val evidenceUrl: String? = null,
    val grade: Double? = null,
    val status: String = "REGISTERED", // REGISTERED | APPROVED | REJECTED
    val timestamp: Timestamp? = null
)
```

## Colecciones Firestore

| Colección | Descripción | Acceso |
|-----------|-------------|--------|
| `users` | Información de usuarios y roles | Según reglas de Firestore |
| `catedras` | Cátedras programadas | Lectura para autenticados, escritura para profesores/admin |
| `attendances` | Registros de asistencia | Según rol del usuario |
