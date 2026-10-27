# Cátedra UCN

[![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Material 3](https://img.shields.io/badge/Material%203-757575?style=for-the-badge&logo=materialdesign&logoColor=white)](https://m3.material.io)
[![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)](https://firebase.google.com)
[![Google Gemini](https://img.shields.io/badge/Google%20Gemini-8E75B2?style=for-the-badge&logo=google&logoColor=white)](https://ai.google.dev)

## Introducción

Cátedra UCN es una aplicación móvil Android nativa que digitaliza la gestión de cátedras en la Universidad Católica del Norte, integrando autenticación, control de asistencia, calificaciones y resúmenes generados con Inteligencia Artificial.

## Problema

En la gestión tradicional de cátedras universitarias, el registro de asistencia es manual, el seguimiento es poco ágil y los estudiantes carecen de resúmenes rápidos para reforzar lo visto en clases. Esto genera pérdida de tiempo, errores en el control y menor aprovechamiento académico.

## Objetivo

Desarrollar una aplicación móvil Android que permita a profesores y estudiantes gestionar cátedras, registrar y calificar asistencias con evidencia, y generar resúmenes automáticos con IA, centralizando todo en una plataforma segura y accesible.

## Pregunta Problema

¿Cómo digitalizar y automatizar la gestión de cátedras, asistencia y apoyo académico en la UCN mediante una aplicación móvil con IA?

## Resumen

Cátedra UCN centraliza la gestión académica mediante roles diferenciados (Estudiante, Profesor y Administrador). Permite crear cátedras, registrar asistencia con evidencia fotográfica, calificarla, seguir cátedras en vivo y generar resúmenes con Google Gemini AI. Desarrollada con Kotlin, Jetpack Compose, Material 3 y Firebase.

## Palabras Clave

`Android` `Kotlin` `Jetpack Compose` `Firebase` `Google Gemini AI` `Asistencia` `Cátedras` `Universidad` `IA` `Aplicación Móvil`

## Tecnologías Empleadas

| Tecnología | Uso |
|------------|-----|
| [Kotlin](https://kotlinlang.org) | Lenguaje principal |
| [Jetpack Compose](https://developer.android.com/jetpack/compose) | UI declarativa |
| [Material 3](https://m3.material.io) | Sistema de diseño |
| [Firebase Auth](https://firebase.google.com/products/auth) | Autenticación |
| [Cloud Firestore](https://firebase.google.com/products/firestore) | Base de datos |
| [Firebase AI (Gemini)](https://firebase.google.com/products/vertex-ai) | Generación de resúmenes con IA |
| [Google Identity Services](https://developers.google.com/identity) | Inicio de sesión con Google |
| [Room](https://developer.android.com/training/data-storage/room) | Almacenamiento local |
| [Navigation Compose](https://developer.android.com/jetpack/compose/navigation) | Navegación |

## Documentación

- [Arquitectura](docs/architecture/overview.md)
- [Decisiones Arquitectónicas (ADRs)](docs/decisions/)
- [Esquema Firestore](docs/api/firestore-schema.md)
