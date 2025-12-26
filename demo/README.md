# 🚗 Sistema de Parqueadero – Reto Técnico Zybo

Este proyecto corresponde a un **sistema backend de gestión de parqueadero**, desarrollado como parte del **reto técnico de Zybo**, utilizando **Java 17 y Spring Boot**.

El sistema expone una **API REST** que permite administrar usuarios, vehículos, ingresos y salidas del parqueadero, garantizando **consistencia de datos, manejo de concurrencia y buenas prácticas de diseño**.

---

## 🎯 Objetivo del reto

Diseñar e implementar un sistema que permita:

- Gestionar **usuarios** y sus **vehículos**
- Registrar **ingresos** y **salidas** de vehículos
- Calcular el **valor a pagar** por el tiempo de parqueo
- Evitar **condiciones de carrera** (concurrencia)
- Simular el envío de eventos mediante una **cola basada en base de datos**

---

## 🧱 Arquitectura

El proyecto sigue una **arquitectura en capas (MVC + Service)**:

### Principios aplicados
- Separación clara de responsabilidades
- DTOs para entrada y salida de datos
- Patrón Builder con Lombok
- Lógica de negocio en la capa Service
- Manejo centralizado de errores
- Control de concurrencia a nivel de base de datos

---

## 🧩 Modelo de dominio

### Usuario
- `id`
- `names`
- `document` (único, no modificable)
- `phone` (único)

### Vehículo
- `id`
- `plate` (única)
- `userId`

### Estancia (Sesión de parqueo)
- `id`
- `vehicleId`
- `entryTime`
- `exitTime`
- `minutesTotal`
- `chargedValue`
- `status` (`OPEN`, `CLOSED`)

### Evento (Cola simulada)
- `id`
- `eventType`
- `payload`
- `status` (`PENDING`, `SENT`)
- `createdAt`

---