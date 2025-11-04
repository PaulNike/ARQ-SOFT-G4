# SGHC — Decisiones Arquitectónicas (Consolidado arc42)
**Versión:** 1.1  
**Fecha:** 2025-11-01  
**Autor:** Comité de Arquitectura SGHC  
**Alcance:** Sección 9 (arc42). Consolida decisiones previas y agrega 10 nuevas ADRs derivadas de los diagramas C2/C3.

---

## Índice
- [SGHC — Decisiones Arquitectónicas (Consolidado arc42)](#sghc--decisiones-arquitectónicas-consolidado-arc42)
  - [Índice](#índice)
  - [9.1 ADR-001 — Estilo arquitectónico](#91-adr-001--estilo-arquitectónico)
    - [Contexto](#contexto)
    - [Decisión](#decisión)
    - [Alternativas consideradas](#alternativas-consideradas)
    - [Consecuencias](#consecuencias)
  - [9.2 ADR-002 — Identidad y autenticación](#92-adr-002--identidad-y-autenticación)
    - [Contexto](#contexto-1)
    - [Decisión](#decisión-1)
    - [Alternativas consideradas](#alternativas-consideradas-1)
    - [Consecuencias](#consecuencias-1)
  - [9.3 ADR-003 — Interoperabilidad clínica](#93-adr-003--interoperabilidad-clínica)
    - [Contexto](#contexto-2)
    - [Decisión](#decisión-2)
    - [Alternativas consideradas](#alternativas-consideradas-2)
    - [Consecuencias](#consecuencias-2)
  - [9.4 ADR-004 — Comunicación por eventos](#94-adr-004--comunicación-por-eventos)
    - [Contexto](#contexto-3)
    - [Decisión](#decisión-3)
    - [Alternativas consideradas](#alternativas-consideradas-3)
    - [Consecuencias](#consecuencias-3)
  - [9.5 ADR-005 — Despliegue y soberanía de datos](#95-adr-005--despliegue-y-soberanía-de-datos)
    - [Contexto](#contexto-4)
    - [Decisión](#decisión-4)
    - [Alternativas consideradas](#alternativas-consideradas-4)
    - [Consecuencias](#consecuencias-4)
  - [9.6 ADR-006 — GitOps y despliegues](#96-adr-006--gitops-y-despliegues)
    - [Contexto](#contexto-5)
    - [Decisión](#decisión-5)
    - [Consecuencias](#consecuencias-5)
  - [9.7 ADR-007 — API Gateway y seguridad de borde](#97-adr-007--api-gateway-y-seguridad-de-borde)
    - [Contexto](#contexto-6)
    - [Decisión](#decisión-6)
    - [Alternativas consideradas](#alternativas-consideradas-5)
    - [Consecuencias](#consecuencias-6)
  - [9.8 ADR-008 — Persistencia por servicio y patrón Outbox](#98-adr-008--persistencia-por-servicio-y-patrón-outbox)
    - [Contexto](#contexto-7)
    - [Decisión](#decisión-7)
    - [Alternativas consideradas](#alternativas-consideradas-6)
    - [Consecuencias](#consecuencias-7)
  - [9.9 ADR-009 — Orquestación de sagas en dominios clínicos](#99-adr-009--orquestación-de-sagas-en-dominios-clínicos)
    - [Contexto](#contexto-8)
    - [Decisión](#decisión-8)
    - [Alternativas consideradas](#alternativas-consideradas-7)
    - [Consecuencias](#consecuencias-8)
  - [9.10 ADR-010 — Resiliencia (timeouts, reintentos, circuit breakers)](#910-adr-010--resiliencia-timeouts-reintentos-circuit-breakers)
    - [Contexto](#contexto-9)
    - [Decisión](#decisión-9)
    - [Consecuencias](#consecuencias-9)
  - [9.11 ADR-011 — Esquemas de eventos y versionado](#911-adr-011--esquemas-de-eventos-y-versionado)
    - [Contexto](#contexto-10)
    - [Decisión](#decisión-10)
    - [Consecuencias](#consecuencias-10)
  - [9.12 ADR-012 — Idempotencia en APIs y consumidores](#912-adr-012--idempotencia-en-apis-y-consumidores)
    - [Contexto](#contexto-11)
    - [Decisión](#decisión-11)
    - [Consecuencias](#consecuencias-11)
  - [9.13 ADR-013 — Observabilidad con OpenTelemetry](#913-adr-013--observabilidad-con-opentelemetry)
    - [Contexto](#contexto-12)
    - [Decisión](#decisión-12)
    - [Consecuencias](#consecuencias-12)
  - [9.14 ADR-014 — Gestión de secretos](#914-adr-014--gestión-de-secretos)
    - [Contexto](#contexto-13)
    - [Decisión](#decisión-13)
    - [Consecuencias](#consecuencias-13)
  - [9.15 ADR-015 — Estrategia de despliegue progresivo](#915-adr-015--estrategia-de-despliegue-progresivo)
    - [Contexto](#contexto-14)
    - [Decisión](#decisión-14)
    - [Consecuencias](#consecuencias-14)
  - [9.16 ADR-016 — Retención y gobernanza de datos clínicos](#916-adr-016--retención-y-gobernanza-de-datos-clínicos)
    - [Contexto](#contexto-15)
    - [Decisión](#decisión-15)
    - [Consecuencias](#consecuencias-15)
  - [9.X Gobierno de ADRs](#9x-gobierno-de-adrs)

---

## 9.1 ADR-001 — Estilo arquitectónico
**Título:** Microservicios modulares con API Gateway  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Necesitamos escalar y mantener múltiples dominios clínicos (pacientes, citas, atención, farmacia, interoperabilidad y auditoría) y habilitar integraciones bajo HL7/FHIR.
Diagramas relacionados: C2-contenedor, C3-api-gateway, C3-identidad, C3-pacientes, C3-citas, C3-clínico, C3-farmacia, C3-interoperabilidad, C3-auditoría.

### Decisión
Adoptar microservicios modulares con API Gateway. Exponer APIs REST documentadas (OpenAPI). Comunicación síncrona mínima; preferir eventos con Kafka. 

### Alternativas consideradas
- Microservicios con patrón hexagonal puro.
- Architecture en capas con patrón hexagonal.

### Consecuencias
+ Escalabilidad independiente por dominio; despliegue incremental.  
− Mayor complejidad operativa y de observabilidad.

---

## 9.2 ADR-002 — Identidad y autenticación
**Título:** Autenticación federada con Keycloak (OIDC)  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Front paciente (web/móvil), módulo clínico, APIs internas/externas y federación con ESSALUD/RENIEC. Cumplimiento OIDC/OAuth2, HIPAA y GDPR.
Diagrama relacionado: C3-identidad.

### Decisión
Keycloak como IdP corporativo en modo federated realm. Roles: Paciente, Medico, Recepcionista, Farmacia, Auditor. Tokens JWT firmados. Auditoría de login.

### Alternativas consideradas
Auth0/Okta, Cognito.

### Consecuencias
+ Control de identidad y auditoría; SSO.  
− Administración interna (backups/upgrades).

---

## 9.3 ADR-003 — Interoperabilidad clínica
**Título:** Estándar HL7 FHIR R4  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Interoperabilidad con laboratorios/aseguradoras. Integridad y trazabilidad clínica.
Diagrama relacionado: C3-interoperabilidad.

### Decisión
Adoptar HL7 FHIR R4 (`Patient`, `Observation`, `Appointment`, `Condition`, `MedicationRequest`) vía REST+JSON con OIDC y TLS.

### Alternativas consideradas
HL7 v2, JSON propietario.

### Consecuencias
+ Interoperabilidad nacional/internacional; menor acoplamiento.  
− Mayor esfuerzo de validación/mapeo FHIR.

---

## 9.4 ADR-004 — Comunicación por eventos
**Título:** Event-driven con Apache Kafka  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Desacoplamiento entre pacientes, citas, recetas y auditoría.
Diagramas relacionados: C3-citas, C3-clínico, C3-farmacia, C3-auditoría.

### Decisión
Eventos clínicos JSON versionados (p.ej. `receta.creada.v1`). Tópicos por agregado y por contexto delimitado.

### Alternativas consideradas
RabbitMQ, REST síncrono.

### Consecuencias
+ Resiliencia, replay, trazabilidad.  
− Complejidad de monitoreo/retención.

---

## 9.5 ADR-005 — Despliegue y soberanía de datos
**Título:** Kubernetes privado (on-prem/cloud privada)  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Soberanía de datos (MINSA, HIPAA), balanceo, HA y CI/CD.
Diagrama relacionado: C3-infraestructura.

### Decisión
K8s privado con Docker, ArgoCD, Prometheus+Grafana, NGINX Ingress, Longhorn/Ceph.

### Alternativas consideradas
VMs tradicionales; nubes públicas.

### Consecuencias
+ HA, elasticidad, cumplimiento.  
− Mayor complejidad de operaciones.

---

## 9.6 ADR-006 — GitOps y despliegues
**Título:** GitOps con ArgoCD  
**Fecha:** 2025-10-13  
**Estado:** ✅ Aprobado

### Contexto
Necesidad de consistencia entre entornos y despliegues auditables.

### Decisión
Declarativo con Helm/Kustomize; reconciliación continua con ArgoCD; promociones entre entornos mediante PRs.

### Consecuencias
+ Trazabilidad y menor error humano.  
− Disciplina en gestión de ramas y plantillas.

---

## 9.7 ADR-007 — API Gateway y seguridad de borde
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
El C2 y C3 de API Gateway muestran un único punto de entrada para portales y apps clínicas; se requiere control transaccional, throttling y observabilidad.

### Decisión
Adoptar **API Gateway** (p. ej. Kong/NGINX) con:
- Autenticación OIDC (delegada a Keycloak) y validación de JWT.
- Rate limiting, cuotas por cliente y protección contra abuso.
- Transformaciones y agregación de respuestas para el Portal Paciente.
- Rutas canónicas por dominio: `/pacientes`, `/citas`, `/clinico`, `/farmacia`, `/interop`, `/auditoria`.
- Exposición de métricas y trazas (OpenTelemetry).

### Alternativas consideradas
BFF por vertical únicamente; múltiples gateways por dominio.

### Consecuencias
+ Seguridad de borde unificada y control de tráfico.  
− Posible cuello de botella si no se escala correctamente.

---

## 9.8 ADR-008 — Persistencia por servicio y patrón Outbox
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Los C3 de pacientes/citas/recetas muestran agregados con transacciones locales y publicación de eventos para sincronización inter-servicios.

### Decisión
- **Base de datos por servicio** (PostgreSQL) para `pacientes`, `citas`, `clinico`, `farmacia`, `auditoria`.
- **Patrón Outbox**: persistir evento junto a la transacción local y publicarlo de forma confiable a Kafka.
- **Read models** denormalizados para consultas del Portal Paciente.

### Alternativas consideradas
BD compartida; triggers directos a Kafka.

### Consecuencias
+ Aislamiento, autonomía y consistencia eventual.  
− Complejidad en sincronización/observabilidad.

---

## 9.9 ADR-009 — Orquestación de sagas en dominios clínicos
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Flujos de negocio multi-servicio: creación/confirmación de citas involucra `pacientes`, `citas`, `clinico` y notificaciones.

### Decisión
- **Coreografía** por eventos para flujos simples (p.ej. cita reservada → notificación).
- **Orquestación** con un *Saga orchestrator* en `citas` para flujos con compensaciones (p.ej. reserva de slot + disponibilidad de médico + cancelación por conflicto).
- Definir **acciones compensatorias** idempotentes.

### Alternativas consideradas
Transacciones distribuidas (2PC); coreografía pura.

### Consecuencias
+ Control explícito de flujos complejos y compensaciones.  
− Mayor complejidad de modelado y pruebas.

---

## 9.10 ADR-010 — Resiliencia (timeouts, reintentos, circuit breakers)
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Integraciones clínicas y dependencias internas/externas requieren control de fallas.

### Decisión
- **Timeouts** coherentes por operación.
- **Reintentos** con *exponential backoff* y jitter.
- **Circuit breakers** (Resilience4j/WebFlux o equivalente).
- **Bulkheads** y **pooling** limitado por cliente.
- **DLQ** en Kafka para mensajes fallidos.

### Consecuencias
+ Estabilidad bajo degradación.  
− Más paths de error que cubrir en testing.

---

## 9.11 ADR-011 — Esquemas de eventos y versionado
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Los C3 event-driven requieren contratos claros y evolutivos.

### Decisión
- Eventos **JSON/Avro** con **Schema Registry**.  
- Convención `{dominio}.{evento}.vN` y *headers* con `correlationId`, `causationId`, `tenantId`.  
- **Compatibilidad hacia atrás** y *deprecation policy*.

### Consecuencias
+ Evolución segura de contratos y *replay* confiable.  
− Gobernanza adicional y *linting* de esquemas.

---

## 9.12 ADR-012 — Idempotencia en APIs y consumidores
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Creación de recursos (citas/recetas) y procesamiento de eventos deben soportar reintentos sin duplicados.

### Decisión
- **Idempotency-Key** en endpoints POST críticos.  
- **Deduplicación** en consumidores Kafka por `eventId`.  
- **Upserts**/checks de unicidad en base de datos.

### Consecuencias
+ Operaciones seguras ante reintentos/redelivery.  
− Estado adicional para rastrear claves/eventos.

---

## 9.13 ADR-013 — Observabilidad con OpenTelemetry
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Necesitamos trazabilidad extremo a extremo (API Gateway ↔ servicios ↔ Kafka ↔ DB).

### Decisión
- **OpenTelemetry** para *traces, metrics, logs*.  
- Export a **Jaeger/Tempo** y **Prometheus/Grafana**.  
- Propagación W3C `traceparent`; *span links* en publicación/consumo Kafka.  
- Tableros por dominio y SLOs por servicio.

### Consecuencias
+ Diagnóstico de fallas y *hot paths*.  
− Sobrecosto mínimo de telemetría.

---

## 9.14 ADR-014 — Gestión de secretos
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Claves OIDC, certificados TLS, credenciales DB y tokens de terceros.

### Decisión
- **Vault** (o Sealed Secrets) para almacenar secretos.  
- Rotación periódica y *least privilege*.  
- Inyección vía **CSI Secrets Store** en K8s.

### Consecuencias
+ Reducción de riesgo de exposición.  
− Operación adicional (HA/backup de Vault).

---

## 9.15 ADR-015 — Estrategia de despliegue progresivo
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Los módulos clínicos requieren liberar cambios sin downtime.

### Decisión
- **Canary/Blue-Green** con **Argo Rollouts**.  
- *Progressive delivery* con métricas de salud y auto-rollback.  
- *Feature flags* para activar capacidades.

### Consecuencias
+ Reducción de riesgo en releases.  
− Complejidad en métricas y *gates*.

---

## 9.16 ADR-016 — Retención y gobernanza de datos clínicos
**Fecha:** 2025-11-01  
**Estado:** ✅ Aprobado

### Contexto
Datos clínicos y auditorías tienen regulaciones estrictas; el C3-auditoría y C3-infraestructura evidencian *pipelines* de almacenamiento y *logs*.

### Decisión
- **Retención** diferenciada por tipo (clínico, operativo, auditoría).  
- **Particionamiento** temporal para historiales voluminosos.  
- **Right-to-erasure** (GDPR) mediante *tombstones* y *selective purge* en *read models*.  
- Políticas de **WORM** para auditoría y cifrado en reposo (AES-256).

### Consecuencias
+ Cumplimiento regulatorio y costos controlados.  
− Procesos adicionales de anonimización y borrado selectivo.

---

## 9.X Gobierno de ADRs
- Ubicación: `/docs/adr/ADR-XXX.md`.  
- Revisión trimestral por Architecture Board.  
- Cada ADR enlaza con atributos de calidad y riesgos.  
- Cambios vía RFC y PR revisados.

