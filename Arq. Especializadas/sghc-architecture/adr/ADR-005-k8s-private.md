## Contexto
Los datos de salud del SGHC deben almacenarse y procesarse dentro de la infraestructura propia del grupo clínico, conforme a normativas locales (MINSA, HIPAA).

El sistema necesita balanceo de carga, alta disponibilidad y despliegue automatizado.

## Decisión
Implementar el SGHC sobre un **clúster privado de Kubernetes**, desplegado en infraestructura local (bare metal o cloud privada), con las siguientes características:
- Orquestación de contenedores (Docker)
- CI/CD mediante ArgoCD
- Monitoreo (Prometheus + Grafana)
- Balanceo (NGINX Ingress)
- Storage persistente (Longhorn o Ceph)

## Alternativas consideradas
- **VM tradicionales:** despliegue manual, poca elasticidad.
- **Cloud pública (AWS/GCP):** no cumple normativas de soberanía de datos.
- **Kubernetes privado:** elasticidad, seguridad, control total.

## Consecuencias
**Positivas**
- Alta disponibilidad y escalabilidad dinámica.
- Cumplimiento regulatorio de soberanía de datos.
- Integración nativa con pipelines DevOps.

**Negativas**
- Mayor complejidad de configuración y soporte.
- Requiere conocimiento avanzado de operaciones.

## Estado
✅ Aprobado.

## Fecha
2025-10-13