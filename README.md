# VehicleManager Backend

Backend desarrollado en Java con Spring Boot para la gestión de vehículos, marcas, clientes, empleados, roles y servicios.

## Características

- API RESTful para CRUD de vehículos, marcas, clientes, empleados, roles y servicios.
- Validaciones automáticas con anotaciones en los modelos.
- Respuestas personalizadas mediante utilidades y manejo global de errores de validación.
- Integración con base de datos MySQL (configurable en `application.properties`).
- Arquitectura modular y escalable.


## Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/vehicleManager_backend.git
   cd vehicleManager_backend
   ```

2. Configura la conexión a la base de datos en `src/main/resources/application.properties`:
   ```
   db.url=jdbc:mysql://localhost:3306/vehicle_manager
   db.username=tu_usuario
   db.password=tu_contraseña
   ```

3. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints Principales

- **Marcas**
  - `GET /api/brand` - Listar marcas
  - `POST /api/brand` - Crear marca
  - `PUT /api/brand/{id}` - Actualizar marca
  - `DELETE /api/brand/{id}` - Eliminar marca

- **Servicios**
  - `GET /api/service` - Listar servicios
  - `POST /api/service` - Crear servicio
  - `PUT /api/service/{id}` - Actualizar servicio
  - `DELETE /api/service/{id}` - Eliminar servicio

*(Consulta los controladores para más endpoints)*

## Ejemplo de JSON para POST

**Marca**
```json
{
  "name": "Toyota"
}
```

**Servicio**
```json
{
  "code": "LLAN-01",
  "name": "Cambio de llantas",
  "description": "Servicio de cambio de llantas para vehículos.",
  "price": 500.00
}
```
