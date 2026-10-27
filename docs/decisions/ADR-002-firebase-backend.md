# ADR-002: Integración con Firebase para backend

## Status
Accepted

## Date
2025-01-02

## Context
La aplicación requiere:
- Autenticación de usuarios con diferentes roles
- Almacenamiento de datos en tiempo real (cátedras, asistencias)
- Almacenamiento de archivos (evidencias de asistencia)
- Escalabilidad sin necesidad de infraestructura propia
- Integración con Google Identity Services

## Decision
Usar **Firebase** como backend como servicio (BaaS) con:
- **Firebase Authentication** para autenticación
- **Cloud Firestore** para base de datos NoSQL
- **Firebase App Check** para seguridad
- **Google Identity Services** para inicio de sesión con Google

## Alternatives Considered

### Backend propio (REST API)
- Pros: Control total, sin vendor lock-in
- Cons: Requiere servidor, mantenimiento, autenticación compleja
- Rejected: Sobrecarga para el alcance del proyecto académico

### Supabase
- Pros: Open source, PostgreSQL, APIs auto-generadas
- Cons: Menor integración con ecosistema Android/Google, configuración adicional
- Rejected: Firebase ofrece mejor integración nativa con herramientas de Google

### AWS Amplify
- Pros: Muy completo, escalable
- Cons: Más complejo de configurar, curva de aprendizaje mayor
- Rejected: Demasiado complejo para este proyecto

## Consequences
- Desarrollo rápido sin backend propio
- Sincronización en tiempo real con Firestore
- Autenticación robusta con múltiples proveedores
- Requiere conexión a internet
- Dependencia del ecosistema Firebase
- Costos potenciales en producción (aunque dentro de límites gratuitos para desarrollo)
