# ADR-001: Uso de Kotlin y Jetpack Compose para desarrollo Android

## Status
Accepted

## Date
2025-01-01

## Context
Se necesita desarrollar una aplicación Android para la gestión de cátedras de la Universidad Católica del Norte (UCN). Los requisitos incluyen:
- Interfaz moderna y nativa para Android
- Soporte para múltiples roles de usuario (Estudiante, Profesor, Administrador)
- Integración con Firebase (Autenticación, Firestore)
- Integración con IA para generar resúmenes de cátedras
- Experiencia de usuario fluida y reactiva

## Decision
Usar **Kotlin** como lenguaje de programación principal y **Jetpack Compose** para la interfaz de usuario.

## Alternatives Considered

### Java + XML Layouts
- Pros: Amplio conocimiento, estable, muchas referencias
- Cons: Código verboso, layouts XML separados, menor productividad
- Rejected: Jetpack Compose ofrece mayor productividad y es el estándar actual de Android

### Flutter
- Pros: Multiplataforma, UI declarativa
- Cons: Mayor tamaño de app, curva de aprendizaje, menos integración nativa
- Rejected: Proyecto enfocado exclusivamente a Android nativo

### Kotlin Multiplatform
- Pros: Código compartido entre plataformas
- Cons: Más complejo para este proyecto únicamente Android, menor madurez para UI
- Rejected: Sobrecarga innecesaria para un proyecto Android-only

## Consequences
- Código conciso y moderno con Kotlin
- UI declarativa con Compose, más fácil de mantener
- Mejor integración con APIs nativas de Android
- Acceso a ecosistema moderno (Material 3, Navigation Compose)
- Curva de aprendizaje para desarrolladores familiarizados con XML
- Requiere minSdk 24+ para compatibilidad óptima con algunas APIs
