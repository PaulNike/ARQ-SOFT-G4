# 1. Comprender el dominio y el proposito del sistema

## 1.1 ¿Cuál es el objetivo principal del sistema?
- Gestionar historias clinicas electronicas de forma unificada para todas las sedes hospitalarias.  
## 1.2 ¿Quién lo usará?
- Paciente, Profesionales de salud, personal adminsitrativo, Laboratorios externo, aseguradoras, farmacia, auditor/regulador, Proveedor Auth Server.

## 1.3 ¿Qué valor de negocio entrega?

- Eficiencia Operativa: reducción de tiempos de registro y atención
- Interoperatibilidad: integración con aseguradoras y laboratorios bajo estandares HL7/FHIR
- Cumplimiento normativo: trazabilidad, seguridad y privacidad segun las regulaciones de salud.
- Exp. del paciente: acceso ágil, digital y onmicanal a su información medica.

# 2. Identificación de actores (personas y sistemas externos)

### Personas:
  - Pacientes
  - Profesional de salud
  - Personal Administrativo
  - Auditor / Regulador
  
### Sistemas Externos
-  Laboratorio Externo
-  Aseguradora
-  Proveedor de Autenticación

### Sistemas internos
- Farmacia

# 3. Definir los limites del sistema:

## Limite interno:
    - ¿Que Modulos principales se encuentran?
    -  Modulo de pacientes 
    -  Modulo clinico
    -  Modulo de integración 
    -  Adapatador de estandar FHIR/HL7
  ## Limite Externo:
    - ¿Que Modulos principales se encuentran fuera?
    -  Aseguradoras
    -  Laboratorios
    -  Proveedor de autenticación 
    -  Reguladores (auditorias y cumplimiento legal)
  ## Protocolos y estándares 
    - HTTPS
    - Oauth2.0
    - FHIR/HL7
# 4. Describir las relaciones:
    - Paciente -> Modulo Paciente: Consulta citas, resultados y recetas.
    - Medico -> Modulo clinico: Registro de atenciones y ordenes, emitir recetas.
    - Recepcionista -> Modulo clinico: Registro de paciente, gestión de la agenda y check-in
    - Farmacia -> Modulo clinico: Validación de recetas, prescripcción de medicamentos.
    - Adapatador de estandar FHIR/HL7 -> Aseguradoras: Autorización   / cobertura
    - Adapatador de estandar FHIR/HL7 -> Laboratorios: Resultados y/o observaciones
    - Auditor -> MOdulo de integración: auditoria / cumplimiento
    - Proveedor de Autenticación -> Modulo de integración: Validación y autenticación.