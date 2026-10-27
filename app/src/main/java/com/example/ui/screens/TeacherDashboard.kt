package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PendingActions
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.data.model.Attendance
import com.example.data.model.User
import com.example.ui.viewmodel.AttendanceViewModel
import com.example.util.UiTranslator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TeacherDashboard(
    viewModel: AttendanceViewModel,
    profile: User
) {
    val attendances by viewModel.getTeacherAttendances(profile.id).collectAsState()
    var selectedAttendance by remember { mutableStateOf<Attendance?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Gestión de Asistencias", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text("Docente: ${profile.name}", style = MaterialTheme.typography.bodySmall)
                    }
                }
            )
        }
    ) { padding ->
        if (attendances.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Assignment, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f))
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("No hay asistencias registradas aún", color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding).fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(attendances) { attendance ->
                    TeacherAttendanceCard(
                        attendance = attendance,
                        onGrade = { selectedAttendance = it }
                    )
                }
            }
        }
    }

    if (selectedAttendance != null) {
        GradeDialog(
            attendance = selectedAttendance!!,
            onDismiss = { selectedAttendance = null },
            onSave = { grade ->
                viewModel.updateAttendance(selectedAttendance!!.copy(grade = grade, status = "GRADED"))
                selectedAttendance = null
            }
        )
    }
}

@Composable
fun TeacherAttendanceCard(attendance: Attendance, onGrade: (Attendance) -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(text = attendance.studentName, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                    Text(text = "Cátedra: ${attendance.catedraId}", style = MaterialTheme.typography.bodySmall)
                }
                
                StatusChip(status = attendance.status)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            if (attendance.grade != null) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Nota: ${attendance.grade}",
                            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { onGrade(attendance) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(if (attendance.grade == null) Icons.Default.PendingActions else Icons.Default.CheckCircle, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(if (attendance.grade == null) "Asignar Nota" else "Editar Nota")
            }
        }
    }
}

@Composable
fun StatusChip(status: String) {
    val context = LocalContext.current
    val translatedStatus = UiTranslator.translateStatus(context, status)
    
    val containerColor = when (status) {
        "GRADED" -> MaterialTheme.colorScheme.primaryContainer
        "EVIDENCE_SUBMITTED" -> MaterialTheme.colorScheme.secondaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant
    }
    
    Surface(
        color = containerColor,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = translatedStatus,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold)
        )
    }
}

@Composable
fun GradeDialog(attendance: Attendance, onDismiss: () -> Unit, onSave: (Double) -> Unit) {
    var gradeStr by remember { mutableStateOf(attendance.grade?.toString() ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text("Calificar a ${attendance.studentName}", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Ingresa una nota para el estudiante basándote en su participación y evidencia.", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = gradeStr,
                    onValueChange = { gradeStr = it },
                    label = { Text("Nota (0.0 - 5.0)") },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { gradeStr.toDoubleOrNull()?.let { onSave(it) } },
                enabled = gradeStr.toDoubleOrNull() != null
            ) { Text("Guardar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
