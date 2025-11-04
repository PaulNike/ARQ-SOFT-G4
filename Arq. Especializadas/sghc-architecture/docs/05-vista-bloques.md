# 5. Vista de Bloques — SGHC

## 5.1 Propósito de la vista

Esta vista se describe la **estructura interna del Sistema de Gestión de Historias Clínicas (SGHC)**, mostrando cómo los diferentes **módulos (bloques)** colaboran para cumplir los requisitos funcionales, no funcionales y los objetivos estrategicos definidos en las secciones anteriores.  

El propósito es ofrecer una visión técnica clara para desarrolladores, DevOps, QA y auditores de cumplimiento, explicando **cómo se organiza la solución en niveles (contenedores y componentes)**, y **qué responsabilidades asume cada bloque**.

---

## 5.2 Principios de diseño aplicados

| Principio | Aplicación en SGHC |
|------------|--------------------|
| **Separación por dominios (DDD)** | Cada bloque corresponde a un subdominio clínico: Pacientes, Citas, Atenciones, Recetas, Auditoría. |
| **Encapsulamiento y autonomía** | Cada microservicio tiene su propia base de datos y contratos API. |
| **Comunicación controlada** | API Gateway y Kafka aseguran comunicación segura y desacoplada. |
| **Infraestructura como código** | Todos los contenedores se despliegan vía Kubernetes + GitOps (ArgoCD). |
| **Observabilidad transversal** | Todos los servicios exponen métricas y logs estructurados con OpenTelemetry. |

---

## 5.3 Diagrama general de contenedores (C2)
Ver `docs/diagrams/plantuml/c2-contenedor.puml`.

## 5.4 Descripción de los bloques principales

Esta sección detalla los **bloques (contenedores o microservicios)** que componen el **SGHC (Sistema de Gestión de Historias Clínicas)**, explicando su propósito, responsabilidades, dependencias, tecnologías clave y puntos de interacción.

Cada bloque es autónomo y despliega sus propias APIs, bases de datos y adaptadores de integración, siguiendo un enfoque **Domain-Driven Design (DDD)** con interoperabilidad mediante **API REST y eventos Kafka**.

---

### Bloque 1 — API Gateway

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Servir como punto único de entrada a todas las APIs del SGHC. Controla autenticación, autorización y enrutamiento. |
| **Responsabilidades** | Validar tokens OIDC, aplicar políticas de seguridad, balancear carga, y auditar peticiones. |
| **Tecnologías** | NGINX / Kong / Keycloak Adapter, TLS 1.3, JWT. |
| **Interacciones** | Recibe solicitudes desde frontends (Portal del Paciente, Módulo Clínico) y las reenvía a microservicios internos. |
| **Motivación arquitectónica** | Desacoplar la capa de presentación de los servicios internos y concentrar la seguridad. |

---

###  Bloque 2 — Módulo de Pacientes

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Gestionar la información demográfica y administrativa de los pacientes. |
| **Responsabilidades** | CRUD de pacientes, vinculación con citas, verificación de identidad (DNI, OIDC), y sincronización con FHIR Patient. |
| **Tecnologías** | Spring Boot / PostgreSQL / FHIR Mapper / REST API. |
| **Dependencias** | API Gateway, Módulo de Citas, Interoperabilidad. |
| **Eventos publicados** | `paciente.creado.v1`, `paciente.actualizado.v1`. |
| **Seguridad** | Datos cifrados en reposo (AES-256) y acceso con RBAC/ABAC. |

---

###  Bloque 3 — Módulo de Citas

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Administrar la agenda médica y las reservas de atención. |
| **Responsabilidades** | Registrar, actualizar y cancelar citas; validar disponibilidad; coordinar con el módulo clínico. |
| **Tecnologías** | Quarkus / PostgreSQL / Kafka / OpenAPI 3.0. |
| **Dependencias** | Pacientes, Clínico, Auditoría. |
| **Eventos publicados** | `cita.creada.v1`, `cita.cancelada.v1`. |
| **Seguridad** | Autenticación OIDC y validación de roles (recepcionista, médico). |

---

###  Bloque 4 — Módulo Clínico

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Gestionar el núcleo de las historias clínicas electrónicas. |
| **Responsabilidades** | Registro de atenciones médicas, diagnósticos (CIE-10), evolución, recetas electrónicas y resultados. |
| **Tecnologías** | Spring Boot / PostgreSQL / HAPI FHIR / Kafka. |
| **Dependencias** | Citas, Pacientes, Interoperabilidad, Farmacia. |
| **Eventos publicados** | `atencion.creada.v1`, `receta.emitida.v1`. |
| **Seguridad** | Autenticación OIDC + cifrado AES-256 + auditoría completa. |

---

###  Bloque 5 — Módulo de Farmacia

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Gestionar la dispensación y validación de recetas electrónicas. |
| **Responsabilidades** | Validar recetas, registrar entregas, integrar con aseguradoras y stock de medicamentos. |
| **Tecnologías** | Node.js / MongoDB / REST API / FHIR MedicationRequest. |
| **Dependencias** | Módulo Clínico, Interoperabilidad. |
| **Eventos publicados** | `receta.dispensada.v1`. |
| **Seguridad** | RBAC por rol (farmacéutico) y logs auditados. |

---

###  Bloque 6 — Módulo de Interoperabilidad (FHIR/HL7)

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Permitir el intercambio seguro de información con sistemas externos (laboratorios, aseguradoras, reguladores). |
| **Responsabilidades** | Traducir modelos internos ↔ FHIR/HL7, exponer endpoints `/fhir/*`, validar mensajes y publicar eventos. |
| **Tecnologías** | HAPI FHIR / Spring Boot / Kafka / JSON + XML. |
| **Dependencias** | Clínico, Pacientes, Auditoría. |
| **Eventos publicados** | `interoperabilidad.resultadoLaboratorio.v1`. |
| **Seguridad** | TLS 1.3, OIDC, cifrado AES-256 en logs y payloads. |

---

###  Bloque 7 — Módulo de Auditoría

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Registrar todas las operaciones clínicas y administrativas realizadas dentro del SGHC. |
| **Responsabilidades** | Capturar eventos de todos los módulos, mantener trazabilidad y cumplir normativas (Ley 29733). |
| **Tecnologías** | Spring Boot / Elastic Stack (ELK) / PostgreSQL / Kafka Consumer. |
| **Dependencias** | Todos los microservicios publicadores. |
| **Eventos consumidos** | Todos los eventos de negocio (`*.v1`). |
| **Seguridad** | Acceso solo lectura, logs firmados digitalmente. |

---

###  Bloque 8 — Módulo de Identidad (Keycloak)

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Gestionar autenticación, autorización y federación de usuarios. |
| **Responsabilidades** | Proveer login OIDC, manejo de roles (RBAC), atributos (ABAC) y tokens JWT para las APIs. |
| **Tecnologías** | Keycloak / OIDC / OAuth2 / LDAP Connector. |
| **Dependencias** | API Gateway, todos los servicios. |
| **Eventos publicados** | `usuario.autenticado.v1`, `rol.actualizado.v1`. |
| **Seguridad** | Cifrado TLS 1.3, rotación de tokens, 2FA opcional. |

---

###  Bloque 9 — Módulo de Infraestructura y Monitoreo

| Atributo | Descripción |
|-----------|-------------|
| **Propósito** | Gestionar despliegues, observabilidad, resiliencia y escalado automático. |
| **Responsabilidades** | Implementar CI/CD, métricas, alertas, trazas distribuidas y logs centralizados. |
| **Tecnologías** | Kubernetes / Prometheus / Grafana / ArgoCD / OpenTelemetry. |
| **Dependencias** | Todos los microservicios. |
| **Eventos publicados** | Alertas y métricas técnicas. |
| **Seguridad** | Acceso restringido DevOps, auditoría de cambios IaC. |

---

###  Referencias cruzadas

- Diagrama general C2: `docs/diagrams/plantuml/c2-contenedor.puml`
- Diagramas de componentes C3: `docs/diagrams/plantuml/c3-*.puml`
- Estrategia de interoperabilidad: ver sección **4.7**
- Estrategia de seguridad: ver sección **4.6**

---

> **Conclusión:**  
> Esta vista de bloques proporciona la base estructural del SGHC, garantizando una arquitectura modular, segura y mantenible.  
> Cada bloque tiene un propósito claro, interfaces bien definidas y cumple principios de **autonomía, desacoplamiento y trazabilidad**, esenciales en sistemas de salud de misión crítica.
