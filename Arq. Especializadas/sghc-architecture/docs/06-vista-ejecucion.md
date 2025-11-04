# 6. Vista de Ejecución — SGHC

## 6.1 Propósito
Describe el comportamiento dinámico del SGHC durante su operación, mostrando las interacciones entre actores, microservicios y sistemas externos.

---

## 6.2 Escenario 1 — Autenticación y acceso al sistema
1. El usuario (paciente o médico) se autentica mediante **Keycloak (OIDC)**.  
2. Keycloak valida credenciales y genera un **token JWT**.  
3. El usuario accede al **API Gateway**, que valida el token.  
4. El Gateway redirige la solicitud al microservicio correspondiente.

---

## 6.3 Escenario 2 — Registro de atención médica
1. El paciente solicita una cita a través del **Módulo de Citas**.  
2. El módulo emite el evento `cita.creada.v1` en **Kafka**.  
3. El médico atiende al paciente y registra la atención en el **Módulo Clínico**.  
4. Se almacenan diagnóstico, receta y observaciones; se publica `atencion.creada.v1`.  
5. El **Módulo de Auditoría** registra la trazabilidad completa.

---

## 6.4 Escenario 3 — Interoperabilidad con sistemas externos
1. El **Laboratorio Externo** envía un resultado mediante **FHIR Observation**.  
2. El **Módulo de Interoperabilidad** valida y convierte el mensaje HL7.  
3. Los datos se almacenan y se publica un evento clínico.  
4. El **Módulo de Auditoría** registra la transacción.

---

## 6.5 Escenario 4 — Monitoreo y alerta
1. Los microservicios exponen métricas mediante **Prometheus Exporter**.  
2. **Prometheus** recolecta las métricas.  
3. **Grafana** las visualiza y **Alertmanager** genera alertas.  
4. El equipo **DevOps/SRE** recibe notificaciones automáticas.
