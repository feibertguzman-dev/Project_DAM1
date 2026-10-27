# Arquitectura del Proyecto Cátedra UCN

## Visión General

Cátedra UCN es una aplicación Android nativa desarrollada con Kotlin y Jetpack Compose que facilita la gestión de cátedras, asistencia y resumenes con IA para la Universidad Católica del Norte.

## Arquitectura General

La aplicación sigue una arquitectura **MVVM (Model-View-ViewModel)** con Clean Architecture principles:

```
┌─────────────────────────────────────────────────────────┐
│                        UI Layer                          │
│  (Jetpack Compose Screens, Theme, Navigation)           │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│                    ViewModel Layer                       │
│  (AuthViewModel, CatedraViewModel, AttendanceViewModel) │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│                   Repository Layer                       │
│         (UserRepository, CatedraRepository, etc.)        │
└─────────────────────┬───────────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────────┐
│                    Data Sources                          │
│        Firebase Firestore, Firebase Auth, Room DB       │
└─────────────────────────────────────────────────────────┘
```

## Estructura de Paquetes

```
com.example/
├── MainActivity.kt           # Punto de entrada de la aplicación
├── data/
│   ├── model/               # Modelos de datos (User, Catedra, Attendance)
│   └── repository/          # Repositorios para acceso a datos
├── ui/
│   ├── screens/             # Pantallas Composables
│   ├── theme/               # Tema, colores, tipografía
│   └── viewmodel/           # ViewModels
└── util/                    # Utilidades (AuthHelper, GeminiClient, UiTranslator)
```

## Componentes Principales

### Data Layer
- **Models**: Clases de datos con `toMap()` para serialización a Firestore
- **Repositories**: Abstracción sobre fuentes de datos, lógica de negocio
- **Local**: Room Database para cache offline (configurado)

### UI Layer
- **Jetpack Compose**: UI declarativa con Material 3
- **Navigation**: Navegación entre pantallas
- **Theme**: Sistema de temas personalizado

### ViewModel Layer
- **State Management**: StateFlow/LiveData para manejo de estado
- **Coroutines**: Operaciones asíncronas

## Flujo de Autenticación

1. Usuario abre la app → `MainActivity` verifica estado de autenticación
2. Si no autenticado → `LoginScreen` (Google Sign-In)
3. Si autenticado pero sin rol → `RoleSelectionScreen`
4. Si autenticado con rol → `MainScreen` con dashboard según rol

## Roles de Usuario

| Rol | Permisos |
|-----|-----------|
| **STUDENT** | Ver cátedras, registrar asistencia, ver resumenes IA |
| **TEACHER** | Crear/gestionar cátedras, revisar asistencias, calificar |
| **ADMIN** | Gestión completa del sistema |

## Integraciones Externas

- **Firebase Authentication**: Autenticación con Google
- **Cloud Firestore**: Base de datos en tiempo real
- **Firebase AI (Gemini)**: Generación de resúmenes
- **Google Identity Services**: Credenciales para inicio de sesión
- **Firebase App Check**: Protección contra abuso
