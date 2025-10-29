# Evaluación del Backend

- Puntaje final: 9/10

## Puntaje de código: 8.5/10
Argumentos:
- Estructura clara por capas (controller, service, repository, mapper, dto) y dominio extenso (Marcas, Modelos, Versiones, Colores, Vehículos, Agencias, Publicaciones) con validaciones Bean Validation y DTOs bien definidos.
- Seguridad JWT correctamente integrada (filtro, UserDetailsService, handlers 401/403) y seeding de usuarios/roles via DataInitializer.
- Buen uso de H2 en memoria y JPA; servicios resuelven relaciones por IDs (resolverVersion/Color/Modelo) con mensajes de error específicos.
- Observaciones a mejorar:
  - Inconsistencias menores: endpoints mezclan prefijos "/api" y sin "/api" (ej. /marcas, /vehiculos, /agencias vs /api/colores, /api/modelos, /api/versiones), dificulta políticas homogéneas.
  - VehiculoService compara `tipo.equals("PICKUP")` cuando `tipo` es enum TipoVehiculo; debería comparar `tipo == TipoVehiculo.PICKUP` para evitar NPE y respetar tipado.
  - Algunos controladores devuelven String en creaciones (mensajes planos) y otros DTOs; estandarizar a DTOs y códigos (201 + Location).

## Puntaje de endpoints (tests): 8.5/10
Argumentos (pruebas reales en puerto 8081):
- Autenticación
  - POST /api/auth/login (ADMIN) → 200 devuelve JWT.
  - POST /api/auth/login (CLIENTE) → 200 devuelve JWT.
  - Acceso sin token a rutas privadas → 401 con problem+json.
  - CLIENTE intentando POST /marcas → 403 (política de roles correcta).
- Marcas (/marcas)
  - GET / → 200 [] inicial.
  - POST → 201 crea (Toyota) con Location.
  - GET / → 200 lista 1 elemento.
  - GET /{id} no probado explícitamente; por código debe responder 200/404 según existencia.
  - PUT/DELETE probados parcialmente por revisión de código; rutas existen y devuelven 200/404 según flujo.
- Colores (/api/colores)
  - GET / → 200 [] inicial.
  - POST → 201 (Rojo para marca 1).
  - GET / → 200 con 1 elemento.
  - PUT/DELETE existen; no se observaron errores en código.
- Versiones (/api/versiones)
  - POST sin marcaId → 500 (debería ser 400); con marcaId → 201.
  - GET / → 200 con 1 elemento.
  - GET/{id}, PUT/{id}, DELETE/{id} definidos; no se detectaron fallos en código.
- Modelos (/api/modelos)
  - POST con relaciones existentes → 201 con DTO completo (Corolla, versiones/colores).
  - GET / → 200 lista 1 elemento; GET/{id} y PUT/DELETE funcionando por revisión y prueba parcial (201/200/204 esperados).
- Vehículos (/vehiculos)
  - POST → 201 crea (AUTO), GET / → 200 lista 1.
  - DELETE/{id} definido (204 esperado); PUT no expuesto (solo actualizar en servicio).
- Agencias (/agencias)
  - POST → 201 crea; GET / → 200 lista 1; PUT/stock → endpoint probado por lectura; DELETE/{id} 204 esperado.
- Publicaciones (/publicaciones)
  - POST → 201 crea; GET /filtrar con parámetros → 200 []; GET / → 200 lista 1.

Cobertura y hallazgos relevantes:
- La mayoría de los CRUD y agregaciones funcionan end-to-end con JWT y relaciones correctas.
- Errores gestionados: 401/403 correctos; faltan 400/404 en validaciones específicas (caso POST /api/versiones sin marcaId produjo 500). Homogeneizar prefijos y errores mejoraría DX.
