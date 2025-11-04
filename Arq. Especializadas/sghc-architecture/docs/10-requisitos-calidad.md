# 10. Requisitos de Calidad y KPIs — SGHC

## 10.1 Propósito
Definir los **atributos de calidad críticos** del SGHC y las **métricas (KPIs)** que permitirán monitorear su cumplimiento.  
Estos indicadores garantizan que la arquitectura cumpla con los objetivos no funcionales definidos en la estrategia técnica (sección 4) y operativa (sección 7).

---

## 10.2 Atributos de calidad principales

| Atributo | Objetivo | Métrica / KPI | Valor esperado |
|-----------|-----------|----------------|----------------|
| **Disponibilidad** | Asegurar acceso 24/7 al sistema clínico. | Uptime mensual. | ≥ 99.8% |
| **Seguridad** | Proteger los datos personales y clínicos. | % de endpoints bajo TLS 1.3. | 100% |
| **Rendimiento** | Garantizar respuestas rápidas. | Tiempo medio de respuesta (p95). | ≤ 500 ms |
| **Escalabilidad** | Soportar aumento de demanda sin degradación. | CPU / Memory AutoScale HPA. | Escala automática activa |
| **Interoperabilidad** | Cumplir estándares internacionales de salud. | Compatibilidad FHIR/HL7 validada. | 100% conformidad |
| **Trazabilidad** | Registrar todas las operaciones médicas. | Tasa de logs auditables / totales. | ≥ 99% |
| **Disponibilidad de datos** | Evitar pérdida de información clínica. | Política de backup / restore test. | Restauración < 30 min |
| **Mantenibilidad** | Facilitar evolución del sistema. | SonarQube Maintainability Index. | ≥ 80 |
| **Resiliencia** | Recuperación ante fallos de servicios. | Tiempo medio de recuperación (MTTR). | < 5 min |
| **Observabilidad** | Detección y análisis de incidencias. | % de microservicios con métricas y logs. | 100% |

---

## 10.3 Escenarios de calidad

### Escenario A — Seguridad y confidencialidad
**Contexto:** Acceso de médico a información clínica sensible.  
**Respuesta esperada:** Autenticación OIDC + autorización RBAC.  
**Mecanismos:** Keycloak, TLS 1.3, AES-256.  
**Medida:** 0 accesos no autorizados reportados por mes.

---

### Escenario B — Escalabilidad en hora punta
**Contexto:** Alta demanda (1000 solicitudes concurrentes).  
**Respuesta esperada:** Autoescalado de pods y balanceo de carga.  
**Mecanismos:** Kubernetes HPA, NGINX Ingress, Kafka.  
**Medida:** < 5% de errores 5xx durante picos.

---

### Escenario C — Recuperación ante falla
**Contexto:** Caída del microservicio de citas.  
**Respuesta esperada:** Reconexión automática y recuperación del flujo.  
**Mecanismos:** Resilience4j + retry + circuit breaker.  
**Medida:** MTTR < 5 min, sin pérdida de datos.

---

### Escenario D — Cumplimiento legal y auditoría
**Contexto:** Inspección por entidad reguladora.  
**Respuesta esperada:** Reportes de trazabilidad completa.  
**Mecanismos:** Módulo de Auditoría + Kafka + Elastic.  
**Medida:** 100% de eventos trazables.

---

## 10.4 Estrategia de medición
- Monitoreo continuo mediante Prometheus + Grafana.  
- Validaciones automáticas en pipelines CI/CD (SonarQube, OWASP).  
- Auditorías semestrales de seguridad y cumplimiento.  
- Revisión de KPIs trimestral por el Comité de Arquitectura y Seguridad.
