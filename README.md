# MS-Bodega

Microservicio encargado de gestionar la bodega frigorifica y los lotes almacenados en la Caleta Lo Abarca.

## Tecnologias
- Java 21
- Spring Boot 4.0.6
- PostgreSQL (Neon)
- Maven

## Puerto
8086

## Endpoints

### Bodegas
- POST /api/bodegas - Crear nueva bodega
- GET /api/bodegas - Obtener todas las bodegas
- GET /api/bodegas/{id} - Obtener bodega por ID
- GET /api/bodegas/activas - Obtener bodegas activas
- PUT /api/bodegas/{id}/temperatura - Actualizar temperatura
- DELETE /api/bodegas/{id} - Desactivar bodega

### Lotes en Bodega
- POST /api/lotes-bodega - Ingresar lote a bodega
- GET /api/lotes-bodega - Obtener todos los lotes
- GET /api/lotes-bodega/{id} - Obtener lote por ID
- GET /api/lotes-bodega/comprador/{compradorId} - Obtener lotes por comprador
- GET /api/lotes-bodega/estado/{estado} - Obtener lotes por estado
- PUT /api/lotes-bodega/{id}/retirar - Retirar lote de bodega

## Como correr el proyecto
1. git clone https://github.com/Malos-pal-catre/ms-bodega.git
2. cd ms-bodega
3. ./mvnw spring-boot:run
