# 7. Vista de Despliegue — SGHC

## 7.1 Entornos de ejecución

| Entorno | Descripción | Infraestructura |
|----------|--------------|----------------|
| **Desarrollo** | Pruebas locales con Docker Compose o Minikube. | PostgreSQL, Keycloak, Prometheus. |
| **Preproducción** | QA y pruebas de carga. | Kubernetes GKE / MinIO / Vault. |
| **Producción** | Alta disponibilidad, monitoreo y CI/CD. | Kubernetes multinodo, TLS 1.3, GitOps. |

---

## 7.2 Infraestructura principal
- **Orquestador:** Kubernetes.  
- **CI/CD:** GitHub Actions + ArgoCD.  
- **Bases de datos:** PostgreSQL, MongoDB, MinIO.  
- **Mensajería:** Kafka.  
- **Identidad:** Keycloak (OIDC).  
- **Monitoreo:** Prometheus, Grafana, ELK Stack.

---

## 7.3 Topología de despliegue
Cada microservicio (Pacientes, Citas, Clínico, Farmacia, Interoperabilidad, Auditoría, Identidad) se ejecuta en un **pod Kubernetes independiente** con su base de datos exclusiva.  
El **API Gateway** y **Keycloak** se despliegan en el namespace de seguridad.  
El namespace **observabilidad** contiene Prometheus, Grafana y Elastic Stack.

---

## 7.4 Seguridad del despliegue
- TLS 1.3 en todos los endpoints.  
- Cifrado AES-256 en bases de datos.  
- Secretos gestionados con **Vault**.  
- Políticas RBAC y **Network Policies** en Kubernetes.

---

## 7.5 CI/CD y versionado
- Repositorios Git independientes.  
- Versionado semántico (`vX.Y.Z`).  
- Despliegues canarios con **Argo Rollouts**.  
- Pipelines automáticos para build, test, deploy y rollback.
