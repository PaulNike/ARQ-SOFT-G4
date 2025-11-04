# Sistema de Gestión de Historias Clínicas (SGHC)
**Documento de Arquitectura de Software — Basado en arc42**

---

## 1️⃣ Información General

| Campo | Descripción |
|--------|-------------|
| **Proyecto** | Sistema de Gestión de Historias Clínicas (SGHC) |
| **Versión del Documento** | 1.0 |
| **Fecha de emisión** | Octubre 2025 |
| **Autor / Arquitecto responsable** | Paul Rodríguez Mijahuanga |
| **Revisores** | Comité de Arquitectura y Seguridad - CoreCode Academy |
| **Framework de Documentación** | arc42 (v9.0) |
| **Repositorio** | [SGHC](https://github.com//sghc) |
| **Licencia** | MIT License |

---

## 2️⃣ Propósito del documento
Describir la **arquitectura técnica, funcional y operacional del sistema SGHC**, asegurando que cumpla con los objetivos de interoperabilidad, seguridad, mantenibilidad y escalabilidad definidos por los lineamientos de arquitectura empresarial y las normas de salud (HL7, FHIR, Ley 29733).

---

## 3️⃣ Resumen Ejecutivo
El **SGHC** es una plataforma modular orientada a microservicios que permite la **gestión integral de historias clínicas electrónicas**, interoperando con laboratorios, aseguradoras y entidades regulatorias bajo estándares internacionales.

Su diseño se basa en los principios de:
- **Arquitectura basada en dominios (DDD)**  
- **Microservicios desacoplados y seguros (REST + Kafka)**  
- **Autenticación federada (OIDC / Keycloak)**  
- **Cifrado avanzado (TLS 1.3 + AES-256)**  
- **Infraestructura como código (Kubernetes + GitOps)**

El documento se estructura en 12 secciones según el estándar **arc42**, abarcando desde el contexto del negocio hasta los riesgos técnicos, decisiones arquitectónicas y KPIs de calidad.

---

## 4️⃣ Resumen de la Solución Técnica

| Área | Solución aplicada |
|------|--------------------|
| **Dominio Clínico** | Módulo principal con registro médico, recetas y diagnósticos (CIE-10). |
| **Identidad** | Keycloak (OIDC) con roles y políticas RBAC/ABAC. |
| **Interoperabilidad** | FHIR R4 + HL7 v2.x, mapeo automático HL7 ↔ JSON. |
| **Persistencia** | PostgreSQL + MongoDB + MinIO (S3). |
| **Mensajería** | Apache Kafka con colas de auditoría y DLQ. |
| **Observabilidad** | Prometheus + Grafana + ELK Stack + OpenTelemetry. |
| **CI/CD** | GitHub Actions + ArgoCD (GitOps). |
| **Seguridad** | TLS 1.3, AES-256, certificados gestionados con Vault. |
| **Cumplimiento legal** | Ley 29733, ISO/IEC 27001, HIPAA. |

---

## 5️⃣ Principales Decisiones de Diseño
1. **Arquitectura de microservicios** basada en dominios clínicos y técnicos.  
2. **Infraestructura orquestada** con Kubernetes y despliegue automatizado (GitOps).  
3. **Estandarización en seguridad** mediante Keycloak y TLS 1.3.  
4. **Interoperabilidad universal** con FHIR R4 / HL7 v2.x.  
5. **Trazabilidad total** mediante Kafka + Auditoría central.  

---

## 6️⃣ Indicadores de Calidad (Resumen)
| Métrica | Objetivo | Valor esperado |
|----------|-----------|----------------|
| Disponibilidad | Uptime mensual | ≥ 99.8% |
| Seguridad | Endpoints cifrados | 100% |
| Rendimiento | Latencia promedio (p95) | ≤ 500 ms |
| Escalabilidad | Autoescalado Kubernetes | Activo |
| Interoperabilidad | Cumplimiento FHIR R4 | 100% |
| Auditoría | Eventos trazables | ≥ 99% |

---

## 7️⃣ Entornos y Operación
- **Desarrollo:** Docker Compose + Minikube.  
- **Preproducción:** Kubernetes (GKE).  
- **Producción:** Kubernetes multinodo con balanceo, autoscaling y TLS 1.3.  
- **Monitoreo:** Prometheus, Grafana, Alertmanager.  
- **Backups:** Programados y cifrados AES-256.  

---

## 8️⃣ Cumplimiento Legal y Normativo
El SGHC cumple los lineamientos técnicos y regulatorios:
- **Ley N° 29733:** Protección de Datos Personales (Perú).  
- **ISO/IEC 27001:** Seguridad de la Información.  
- **HIPAA:** Normativa internacional en interoperabilidad médica.  
- **HL7 / FHIR R4:** Estándares de intercambio clínico.

---

## 9️⃣ Conclusión
La arquitectura propuesta del SGHC garantiza una solución **escalable, segura, interoperable y auditable**, alineada con los estándares internacionales y las mejores prácticas de ingeniería de software moderno.

---

## 10️⃣ Firma y Aprobaciones

| Rol | Nombre | Firma | Fecha |
|------|----------|---------|--------|
| Arquitecto Principal | Paul Rodríguez Mijahuanga | ___________ | ___/___/2025 |
| Comité de Seguridad | Consultora Palo Alto | ___________ | ___/___/2025 |
| Jefe de Desarrollo | [Nombre responsable] | ___________ | ___/___/2025 |
