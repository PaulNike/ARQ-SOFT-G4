## Contexto
El SGHC requiere un sistema de autenticación federado y seguro, capaz de integrarse con:
- Portal del paciente (front web/móvil)
- Módulo clínico (profesionales de salud)
- APIs internas y externas (FHIR)
- Aseguradoras y laboratorios (proveedores externos)

Debe cumplir los estándares **OIDC/OAuth2** y normativas de privacidad (HIPAA, GDPR).

## Decisión
Adoptar **Keycloak** como proveedor interno de autenticación (IdP corporativo), federado con posibles proveedores externos (Google, ESSALUD, RENIEC) mediante OIDC.

### Configuración base:
- Modo *federated realm* para autenticación unificada.
- Roles: `Paciente`, `Medico`, `Recepcionista`, `Farmacia`, `Auditor`.
- Emisión de tokens JWT firmados para consumo interno.
- Auditoría de login/logout integrada con módulo de trazabilidad.

## Alternativas consideradas
- **Auth0 / Okta:** opciones comerciales, pero costosas y dependientes de nube pública.
- **Cognito (AWS):** buena opción cloud, pero no cumple políticas locales (infra privada).
- **Keycloak:** open-source, soporte comunitario, despliegue local, cumple estándares.

## Consecuencias
**Positivas**
- Cumplimiento HIPAA/GDPR.
- Control total sobre la identidad y auditoría.
- Escalabilidad y compatibilidad OIDC/OAuth2.

**Negativas**
- Requiere administración interna (backups, upgrades).
- Curva de aprendizaje moderada.

## Estado
✅ Aprobado.

## Fecha
2025-10-13
