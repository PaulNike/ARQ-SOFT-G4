## Contexto
Los módulos del SGHC (pacientes, citas, recetas, auditoría) deben comunicarse de forma desacoplada, garantizando disponibilidad incluso si un servicio se detiene.

## Decisión
Adoptar una **arquitectura basada en eventos (Event-driven)** utilizando **Apache Kafka** como broker principal para mensajería asíncrona entre microservicios.

Cada evento clínico se publica como un mensaje JSON versionado (ejemplo: `receta.creada.v1`), y los consumidores lo procesan según suscriptores definidos.

## Alternativas consideradas
- **RabbitMQ:** simple pero orientado a colas, no a streams de datos.
- **REST síncrono:** fácil de implementar pero genera acoplamiento fuerte.
- **Kafka:** soporta streams, replay y escalabilidad horizontal.

## Consecuencias
**Positivas**
- Desacoplamiento entre módulos clínicos.
- Mayor resiliencia ante fallos temporales.
- Mejor trazabilidad mediante auditoría de eventos.

**Negativas**
- Mayor complejidad de monitoreo y soporte.
- Necesidad de políticas de retención y versionado de mensajes.

## Estado
✅ Aprobado.

## Fecha
2025-10-13