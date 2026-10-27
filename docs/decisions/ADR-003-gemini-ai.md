# ADR-003: Integración con Google Gemini AI para resúmenes

## Status
Accepted

## Date
2025-01-03

## Context
Los profesores necesitan generar resúmenes automáticos de las cátedras para facilitar el estudio de los estudiantes. Se requiere:
- Generación de resúmenes basada en contenido de la cátedra
- Integración con servicios de IA
- Manejo seguro de API keys

## Decision
Usar **Google Gemini AI** a través de **Firebase AI SDK** para generar resúmenes de cátedras.

## Alternatives Considered

### OpenAI GPT
- Pros: Muy popular, modelos potentes
- Cons: Requiere cuenta separada, costos pueden ser mayores
- Rejected: Firebase AI ya está integrado en el proyecto

### Anthropic Claude
- Pros: Buenos resúmenes, buen contexto
- Cons: Integración más compleja, no integrado con Firebase
- Rejected: Menor integración con ecosistema existente

### Modelos locales
- Pros: Sin costos de API, funciona offline
- Cons: Requiere GPU potente en dispositivo, modelos grandes, menor calidad
- Rejected: No viable para la mayoría de dispositivos móviles

## Consequences
- Generación automática de resúmenes con IA
- Integración nativa con Firebase
- API key gestionada con Secrets Gradle Plugin
- Requiere conexión a internet
- Dependencia de servicio externo de Google
- Costos por uso de API (monitorear cuota)
