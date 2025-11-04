# 8. Conceptos Transversales — SGHC

## 8.1 Seguridad
- **Autenticación:** OpenID Connect (Keycloak).  
- **Autorización:** RBAC + ABAC.  
- **Cifrado:** TLS 1.3 en tránsito, AES-256 en reposo.  
- **Firma digital:** en recetas, logs y auditorías.  
- **Gestión de secretos:** HashiCorp Vault.

---

## 8.2 Comunicación
- **REST APIs:** JSON + OpenAPI 3.0.  
- **Mensajería:** Apache Kafka para eventos clínicos.  
- **Interoperabilidad:** HL7 v2.x y FHIR R4.  
- **Protocolos:** HTTPS, WebSocket, gRPC.

---

## 8.3 Persistencia
- PostgreSQL (estructurado), MongoDB (documental), MinIO (archivos).  
- Cifrado AES-256 en almacenamiento y backups.  
- Acceso exclusivo por microservicio.

---

## 8.4 Observabilidad
- **Métricas:** Prometheus.  
- **Logs:** ELK Stack (Elastic, Logstash, Kibana).  
- **Trazabilidad:** OpenTelemetry.  
- **Alertas:** Alertmanager + Slack.  
- **KPIs:** disponibilidad, latencia, errores, rendimiento.

---

## 8.5 Cumplimiento normativo
- Ley 29733 (Protección de Datos Personales - Perú).  
- ISO/IEC 27001 (Seguridad de la Información).  
- HIPAA (para interoperabilidad internacional).  
- HL7 / FHIR (estándares médicos).

---

## 8.6 Infraestructura como código
- Despliegue reproducible con **Terraform** y **Helm Charts**.  
- **GitOps** con ArgoCD.  
- Validaciones y pruebas automáticas en **pipelines CI/CD**.
