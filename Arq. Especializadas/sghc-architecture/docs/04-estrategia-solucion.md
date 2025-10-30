# 4. Estrategia de Solución — SGHC

## 4.1 Propósito general

El **Sistema de Gestión de Historias Clínicas (SGHC)** busca consolidar la información médica de todas las sedes de Salud Integral en un expediente único, seguro y accesible.  
La arquitectura propuesta debe garantizar disponibilidad 24/7, cumplimiento normativo (HIPAA, MINSA, GDPR) e interoperabilidad bajo estándares HL7/FHIR.

La presente sección describe la **estrategia arquitectónica global**, las decisiones clave y la forma en que el diseño técnico satisface los requisitos funcionales y no funcionales (NFRs).

---

## 4.2 Principios arquitectónicos rectores

| Principio | Justificación |
|------------|----------------|
| **Dominio primero (DDD)** | Los microservicios se organizan por subdominios clínicos: Pacientes, Citas, Atenciones, Recetas, Auditoría. Esto mejora la cohesión, evita duplicidades y facilita la escalabilidad funcional. |
| **API-First (OpenAPI/AsyncAPI)** | Garantiza contratos claros y versionados, habilitando desarrollo paralelo y control de dependencias. |
| **Comunicación híbrida (REST/gRPC/Eventos)** | REST para comunicación externa, gRPC para comunicación interna eficiente, y Kafka para eventos clínicos asíncronos. |
| **Seguridad por diseño (Zero-Trust)** | Implementa autenticación centralizada (OIDC), cifrado TLS 1.3 y auditoría inmutable. |
| **Resiliencia** | Patrones Outbox, Idempotencia y Circuit Breaker para tolerancia a fallos. |
| **Observabilidad** | Trazas E2E con OpenTelemetry y métricas RED/USE. |
| **Evolución segura** | Versionado semántico y GitOps para despliegues controlados. |

---

## 4.3 Vista general de la solución

El **SGHC** se compone de microservicios independientes desplegados sobre un **clúster Kubernetes privado**, orquestados mediante **ArgoCD** (GitOps).  
Cada servicio tiene su propia base de datos (PostgreSQL) y se comunica mediante **Kafka (asíncrono)** o **gRPC (interno)**.  
Los documentos clínicos (PDF, imágenes, órdenes) se almacenan en **MinIO/S3 compatible**.

El API Gateway centraliza autenticación, auditoría y rate-limiting.  
El Portal del Paciente se ofrece como aplicación web React con integración directa al backend mediante tokens OIDC.

---

## 4.4 Decisiones arquitectónicas clave

| ID | Decisión | Estado | Referencia |
|----|-----------|---------|-------------|
| ADR-001 | Arquitectura base — Microservicios modulares | ✅ Aprobado | [../adr/ADR-001-arquitectura-base.md](../adr/ADR-001-arquitectura-base.md) |
| ADR-002 | Autenticación — Keycloak (OIDC) | ✅ Aprobado | [../adr/ADR-002-auth-keycloak.md](../adr/ADR-002-auth-keycloak.md) |
| ADR-003 | Interoperabilidad — HL7/FHIR R4 | ✅ Aprobado | [../adr/ADR-003-fhir-r4.md](../adr/ADR-003-fhir-r4.md) |
| ADR-004 | Integración — Event-driven con Kafka | ✅ Aprobado | [../adr/ADR-004-event-driven.md](../adr/ADR-004-event-driven.md) |
| ADR-005 | Despliegue — Kubernetes privado | ✅ Aprobado | [../adr/ADR-005-k8s-private.md](../adr/ADR-005-k8s-private.md) |

---

## 4.5 Alineación con requisitos no funcionales (NFR → Estrategia)

| NFR | Estrategia aplicada | Verificación |
|------|--------------------|---------------|
| **Disponibilidad 99.9%** | Clúster Kubernetes HA + Health probes + failover automático | Métricas Prometheus (SLO/SLA) |
| **RTO ≤ 30min / RPO ≤ 5min** | Backups incrementales + replicación | Pruebas DR trimestrales |
| **Rendimiento (P95 ≤ 300/700ms)** | gRPC interno + caché de lectura | k6 / JMeter |
| **Seguridad (Zero-Trust)** | OIDC + MFA + TLS 1.3 + cifrado AES-256 | Pentesting / SAST-DAST |
| **Cumplimiento normativo** | Auditoría inmutable + retención ≥10 años | Revisión regulatoria MINSA |
| **Interoperabilidad FHIR** | `ms-interoperabilidad` con endpoints REST estándar | Validadores FHIR |
| **Observabilidad** | OpenTelemetry + Grafana + Alertmanager | Dashboards SRE |
| **Portabilidad** | Manifiestos Kubernetes agnósticos | Deploy alternativo validado |
| **Accesibilidad** | WCAG 2.1 AA (portal paciente) | Auditoría UX trimestral |
| **Costo controlado** | FinOps mensual y monitoreo de costos infra | Reportes financieros mensuales |

---

## 4.6 Estrategia de seguridad

- **Identidad:** Keycloak (OIDC) como IdP corporativo, autenticación federada, MFA, tokens JWT firmados.  
- **Autorización:** RBAC por rol (`Paciente`, `Medico`, `Farmacia`, `Auditor`) y ABAC por contexto (sede, consentimiento).  
- **Transporte:** HTTPS + TLS 1.3 en todas las capas.  
- **Datos en reposo:** cifrado AES-256.  
- **Auditoría:** logs inmutables, hash SHA-256, retención mínima 10 años.  
- **Cumplimiento:** alineado a HIPAA, GDPR y lineamientos del MINSA.

---

## 4.7 Estrategia de interoperabilidad

- Implementación del estándar **HL7 FHIR R4** gestionado por el microservicio `ms-interoperabilidad`.  
- Recursos soportados: `Patient`, `Observation`, `Condition`, `MedicationRequest`, `Appointment`.  
- Validación de conformidad mediante servidor FHIR externo (HAPI).  
- Integración REST + autenticación OIDC para laboratorios y aseguradoras.  
- Mensajería asíncrona (Kafka) para sincronización offline y resiliencia.

---

## 4.8 Estrategia de despliegue y evolución

- **Infraestructura:** Kubernetes privado (bare metal o nube privada).  
- **Despliegue:** CI/CD con ArgoCD, pipelines GitOps.  
- **Entornos:** Dev / QA / UAT / Prod.  
- **Escalabilidad:** autoscaling horizontal (HPA), métricas CPU/RAM/eventos.  
- **Monitoreo:** Prometheus + Grafana + Alertmanager.  
- **Evolución:** versionado semántico de APIs, backward compatibility, ADRs actualizados ante cada cambio estructural.

---

## 4.9 Trazabilidad con objetivos de negocio

| Objetivo estratégico | Elemento técnico asociado |
|-----------------------|----------------------------|
| **Eficiencia operativa (−40% tiempo de atención)** | Microservicios + UX optimizada + API-First |
| **Seguridad y confianza del paciente** | Keycloak + Auditoría + cifrado TLS/AES |
| **Interoperabilidad institucional** | FHIR R4 + Kafka + API Gateway |
| **Escalabilidad a múltiples sedes** | Kubernetes + despliegue modular |
| **Cumplimiento normativo** | Auditoría inmutable + almacenamiento local |
| **Experiencia digital 360°** | Portal paciente accesible (WCAG) + SPA React |

---

## 4.10 Riesgos y mitigaciones

| Riesgo | Impacto | Mitigación |
|--------|----------|-------------|
| Complejidad de microservicios | Medio | Documentar contratos y gobernanza API |
| Sobrecarga de infraestructura K8s | Alto | Automatización DevOps + FinOps |
| Integraciones externas lentas | Alto | Caching + mensajería asíncrona |
| Curva de aprendizaje Keycloak | Medio | Capacitación DevSecOps + documentación interna |
| Dependencia de FHIR | Medio | Adaptadores y validadores desacoplados |

---

## 4.11 Conclusión

La **Estrategia de Solución del SGHC** establece un marco arquitectónico sólido, basado en microservicios modulares, seguridad avanzada, y estándares internacionales de interoperabilidad.  
Las decisiones documentadas en los ADRs garantizan que el sistema sea **escalable, auditable, resiliente y alineado con los objetivos del negocio de salud digital**.

> **Resultado:** una arquitectura preparada para evolucionar hacia un ecosistema clínico nacional interoperable, confiable y sostenible.

---

📅 **Fecha:** 2025-10-13  

