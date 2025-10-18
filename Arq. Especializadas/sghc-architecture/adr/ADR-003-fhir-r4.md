## Contexto
El Ministerio de Salud exige compatibilidad con estándares internacionales de intercambio clínico.  
El SGHC debe interoperar con laboratorios y aseguradoras externas, asegurando integridad y trazabilidad de los datos clínicos.

## Decisión
Adoptar el estándar **HL7 FHIR R4** como formato de intercambio clínico estructurado.  
El módulo `ms-interoperabilidad` gestionará la transformación y validación de recursos FHIR:

- Paciente → `Patient`
- Observación → `Observation`
- Cita → `Appointment`
- Diagnóstico → `Condition`
- Receta → `MedicationRequest`

La comunicación con sistemas externos se realizará vía REST + JSON, con autenticación OIDC y cifrado TLS.

## Alternativas consideradas
- **HL7 v2:** ampliamente usado, pero complejo y difícil de mantener.
- **Integración propietaria (JSON custom):** rápida, pero sin interoperabilidad estándar.
- **FHIR R4:** moderno, JSON nativo, compatible con REST APIs.

## Consecuencias
**Positivas**
- Cumple estándares de interoperabilidad nacional e internacional.
- Reduce acoplamiento entre sistemas.
- Simplifica integración futura con ESSALUD u otros proveedores.

**Negativas**
- Requiere mapeo y validación FHIR.
- Incrementa la complejidad inicial del módulo de integración.

## Estado
✅ Aprobado.

## Fecha
2025-10-13