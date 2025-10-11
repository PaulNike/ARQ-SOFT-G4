# 1. Introducción y Objetivos

> **Versión:** 1.0  
> **Autor:** Paul Rodríguez (Arquitecto de Software)  
> **Última actualización:** 2025-10-10

## 1.1 Contexto actual

La red de clínicas **Salud Integral** opera en 5 sedes urbanas con aproximadamente **3.000 consultas diarias**.  
Cada sede administra historias clínicas de forma **descentralizada y heterogénea**, con parte de la información en papel y otra en bases de datos no sincronizadas:contentReference[oaicite:0]{index=0}.  
Esto genera duplicidades, errores clínicos y deficiencias en trazabilidad y cumplimiento regulatorio.

## 1.2 Problema

- No existe un expediente clínico único por paciente.  
- Falta de acceso remoto seguro para pacientes y profesionales.  
- Procesos manuales que incrementan errores y tiempos de atención.  
- Cumplimiento normativo deficiente ante auditorías.

## 1.3 Visión

Proveer un **expediente clínico único, seguro y accesible** para todas las sedes, profesionales y pacientes, reduciendo tiempos, errores y riesgos regulatorios, e integrando laboratorios, farmacias y aseguradoras:contentReference[oaicite:1]{index=1}.

## 1.4 Objetivos estratégicos

1. Reducir 60% el tiempo de registro clínico (15 → 6 min).  
2. Disminuir 80% las historias duplicadas.  
3. Alcanzar 100% de cumplimiento regulatorio.  
4. 90% de adopción del portal paciente.  
5. Integración FHIR/HL7 en Fase 2:contentReference[oaicite:2]{index=2}:contentReference[oaicite:3]{index=3}.

## 1.5 Métricas (KPIs)

| Métrica | Valor meta | Horizonte |
|----------|-------------|------------|
| Tiempo de registro clínico | ≤6 min | 6 meses post-MVP |
| Historias duplicadas | -80% | 9 meses |
| Auditorías regulatorias aprobadas | 100% | 12 meses |
| Adopción del portal | ≥90% | 12 meses |
| Fugas de datos | 0 incidentes | Año 1 |
