
## Contexto
El SGHC requiere un estilo que nos permita escalar y mantener para poder gestionar multiples dominios clinicos: citas, atención medica, recetas, laboratorios y auditoria. Ademas debe permitir integraciones futuras con aseguradoras y laboratorios clinicos bajo el estandar HL7 / FHIR.

Se evaluaron los siguientes enfoques:
- **Microservicios con Patron Hexagonal** (Escalado y despliegue independiente, Mayor complejidad operativa y monitoreo)
- **Arquitectura en Capas (Layered Architecture) Patron Hexagonal** (Diseño claro y mantenible, Requiere disciplina para mantener modularidad)
- **Microservicios Modulares con Api Gateway** (resiliencia, complejidad operativa)

## Decisión
Adoptar el estilo de Microservicios Modulares con Api Gateway, cada servicio va representar un modulo clinico.

Cada microservicio exponer Api Rest, documentadas con OpenApi, se podra utilizar mensajeria asincrona (kafka) y se compartira con un api gateway.

## Alternativas consideradas
- **Microservicios con Patron Hexagonal** (Escalado y despliegue independiente, Mayor complejidad operativa y monitoreo)
- **Arquitectura en Capas (Layered Architecture) Patron Hexagonal** (Diseño claro y mantenible, Requiere disciplina para mantener modularidad)
## Consecuencias
**Positivas**
- Escalabilidad independiente por dominio clínico.
- Facilidad de mantenimiento y despliegue incremental.
- Soporte nativo a estándares (FHIR, REST).

**Negativas**
- Mayor complejidad en infraestructura y monitoreo.
- Necesidad de un equipo DevOps más maduro.
- Riesgo de sobre-fragmentación si no se gobierna adecuadamente.

## Estado
✅ Aprobado por el comité técnico (SGHC Architecture Board).

## Fecha
2025-10-13