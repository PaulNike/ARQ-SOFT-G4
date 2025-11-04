# 11. Riesgos y Deuda Técnica — SGHC

## 11.1 Propósito
Identificar los **riesgos técnicos y organizacionales** asociados al SGHC, así como las áreas donde existen **deudas técnicas o arquitectónicas** que podrían comprometer su evolución o estabilidad.

---

## 11.2 Riesgos arquitectónicos

| ID | Riesgo | Impacto | Probabilidad | Mitigación |
|----|--------|----------|---------------|-------------|
| R1 | Sobrecarga de microservicios bajo picos de tráfico. | Caídas o latencias elevadas. | Media | HPA + balanceadores NGINX. |
| R2 | Dependencia de Keycloak para autenticación. | Interrupción total de login. | Alta | Replica HA + caché JWT local. |
| R3 | Errores en interoperabilidad FHIR/HL7. | Pérdida o corrupción de datos clínicos. | Media | Validadores automáticos de esquema. |
| R4 | Errores de configuración en CI/CD. | Despliegues fallidos o inconsistentes. | Media | GitOps + validaciones previas ArgoCD. |
| R5 | Falta de capacitación del equipo médico. | Riesgo operacional y de soporte. | Media | Capacitación continua + manuales. |
| R6 | Vulnerabilidades de seguridad (OWASP). | Exposición de datos sensibles. | Alta | Escáneres OWASP ZAP y SonarCloud. |
| R7 | Falla en los canales de mensajería Kafka. | Pérdida temporal de auditoría. | Baja | Cluster redundante + DLQ (cola muerta). |

---

## 11.3 Deuda técnica identificada

| ID | Descripción | Impacto | Plan de remediación | Prioridad |
|----|--------------|----------|----------------------|------------|
| DT1 | Falta de pruebas automáticas en el Módulo Farmacia. | Riesgo en despliegues. | Implementar tests unitarios e integración. | Alta |
| DT2 | Documentación incompleta del API Interoperabilidad. | Dificulta integración con terceros. | Generar OpenAPI 3.0 y publicarla en portal. | Media |
| DT3 | Falta de canary releases en auditoría. | Riesgo de fallos invisibles. | Configurar Argo Rollouts con métricas. | Media |
| DT4 | Retraso en implementación de cache local en Citas. | Afecta rendimiento bajo alta demanda. | Añadir Redis con TTL configurado. | Alta |
| DT5 | Falta de pruebas de restauración en backups clínicos. | Riesgo ante pérdida de datos. | Ejecución trimestral de pruebas de recuperación. | Alta |

---

## 11.4 Estrategia de control y seguimiento
- Revisión mensual de riesgos técnicos.  
- Evaluación de deuda técnica en reuniones de arquitectura.  
- Clasificación según impacto en los KPIs definidos en la sección 10.  
- Incorporación de mitigaciones en el backlog técnico del proyecto.  
- Auditorías externas semestrales para verificar avances.

---

## 11.5 Indicadores de madurez arquitectónica
| Dimensión | Indicador | Nivel actual | Meta |
|------------|------------|---------------|------|
| Seguridad | Cumplimiento OWASP Top 10 | 80% | 100% |
| Escalabilidad | HPA configurado por microservicio | 70% | 100% |
| Observabilidad | Métricas y logs integrados | 90% | 100% |
| Documentación | ADRs y diagramas actualizados | 85% | 100% |
| CI/CD | Pipelines automatizados y validados | 75% | 100% |
