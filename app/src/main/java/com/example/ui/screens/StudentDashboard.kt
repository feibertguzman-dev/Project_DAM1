package com.example.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.VideoLibrary
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.Attendance
import com.example.data.model.Catedra
import com.example.data.model.User
import com.example.ui.viewmodel.AttendanceViewModel
import com.example.ui.viewmodel.CatedraViewModel
import com.example.util.UiTranslator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentDashboard(
    catedraViewModel: CatedraViewModel,
    attendanceViewModel: AttendanceViewModel,
    profile: User
) {
    val catedras by catedraViewModel.catedras.collectAsState()
    val attendances by attendanceViewModel.getStudentAttendances(profile.id).collectAsState()
    val teachers by attendanceViewModel.teachers.collectAsState()

    var selectedCatedra by remember { mutableStateOf<Catedra?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Column {
                        Text("Mis Cátedras", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold))
                        Text("Hola, ${profile.name}", style = MaterialTheme.typography.bodySmall)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding).fillMaxSize()) {
            Text(
                text = "Eventos Disponibles",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(16.dp)
            )
            
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 80.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(catedras) { catedra ->
                    val isRegistered = attendances.any { it.catedraId == catedra.id }
                    CatedraCard(
                        catedra = catedra,
                        isRegistered = isRegistered,
                        onRegister = { selectedCatedra = it }
                    )
                }
            }
        }
    }

    if (selectedCatedra != null) {
        RegisterAttendanceDialog(
            catedra = selectedCatedra!!,
            teachers = teachers,
            onDismiss = { selectedCatedra = null },
            onConfirm = { teacher ->
                attendanceViewModel.register(Attendance(
                    studentId = profile.id,
                    studentName = profile.name,
                    catedraId = selectedCatedra!!.id,
                    teacherId = teacher.id,
                    teacherName = teacher.name
                ))
                selectedCatedra = null
            }
        )
    }
}

@Composable
fun CatedraCard(
    catedra: Catedra,
    isRegistered: Boolean,
    onRegister: (Catedra) -> Unit
) {
    val context = LocalContext.current
    val translatedStatus = UiTranslator.translateStatus(context, catedra.status)
    
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = if (isRegistered) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface
        )
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = translatedStatus,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                
                if (isRegistered) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = "Registrado",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Text(
                text = catedra.title,
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            
            Spacer(modifier = Modifier.height(8.dp))
            
            InfoRow(icon = Icons.Default.CalendarToday, text = "${catedra.date} @ ${catedra.time}")
            
            if (catedra.aiSummary != null) {
                Spacer(modifier = Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Resumen IA", style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold))
                        }
                        Text(
                            text = catedra.aiSummary!!,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (catedra.streamUrl != null) {
                    OutlinedButton(
                        onClick = { /* Open stream */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.VideoLibrary, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("Ver En Vivo")
                    }
                }
                
                Button(
                    onClick = { onRegister(catedra) },
                    modifier = Modifier.weight(1f),
                    enabled = !isRegistered,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(if (isRegistered) "Registrado" else "Asistir")
                }
            }
        }
    }
}

@Composable
fun InfoRow(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterAttendanceDialog(
    catedra: Catedra,
    teachers: List<User>,
    onDismiss: () -> Unit,
    onConfirm: (User) -> Unit
) {
    var selectedTeacher by remember { mutableStateOf<User?>(null) }
    var expanded by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        shape = RoundedCornerShape(28.dp),
        title = { Text("Registrar Asistencia", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text(
                    "Para registrar tu asistencia a '${catedra.title}', selecciona el profesor con quien cursas la materia.",
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded }
                ) {
                    TextField(
                        value = selectedTeacher?.name ?: "Seleccionar Profesor",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        teachers.forEach { teacher ->
                            DropdownMenuItem(
                                text = { Text(teacher.name) },
                                onClick = {
                                    selectedTeacher = teacher
                                    expanded = false
                                }
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { selectedTeacher?.let { onConfirm(it) } },
                enabled = selectedTeacher != null
            ) { Text("Confirmar") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancelar") }
        }
    )
}
