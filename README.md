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
  - `GET /api/brand/{id}` - Obtener marca por ID
  - `POST /api/brand` - Crear marca
  - `PUT /api/brand/{id}` - Actualizar marca
  - `DELETE /api/brand/{id}` - Eliminar marca

- **Servicios**
  - `GET /api/service` - Listar servicios
  - `GET /api/service/{id}` - Obtener servicio por ID
  - `POST /api/service` - Crear servicio
  - `PUT /api/service/{id}` - Actualizar servicio
  - `DELETE /api/service/{id}` - Eliminar servicio

- **Vehículos**
  - `GET /api/vehicle` - Listar vehículos
  - `GET /api/vehicle/{id}` - Obtener vehículo por ID
  - `POST /api/vehicle` - Crear vehículo
  - `PUT /api/vehicle/{id}` - Actualizar vehículo
  - `DELETE /api/vehicle/{id}` - Eliminar vehículo

- **Clientes**
  - `GET /api/customer` - Listar clientes
  - `GET /api/customer/{id}` - Obtener cliente por ID
  - `POST /api/customer` - Crear cliente
  - `PUT /api/customer/{id}` - Actualizar cliente
  - `DELETE /api/customer/{id}` - Eliminar cliente

- **Empleados**
  - `GET /api/employee` - Listar empleados
  - `GET /api/employee/{id}` - Obtener empleado por ID
  - `POST /api/employee` - Crear empleado
  - `PUT /api/employee/{id}` - Actualizar empleado
  - `DELETE /api/employee/{id}` - Eliminar empleado

- **Ventas**
  - `POST /api/sale` - Registrar una venta

- **Estadísticas de vehículos**
  - `GET /api/vehicle/sold/count` - Obtener cantidad de autos vendidos
  - `GET /api/vehicle/available/count` - Obtener cantidad de autos disponibles

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

**Vehículo**
```json
{
  "model": "Corolla",
  "color": "Rojo",
  "price": 250000.00,
  "brand": { "id": 1 },
}
```
> Para el POST de vehículo puedes enviar solo el id de la marca, modelo, color y precio

**Cliente**
```json
{
  "full_name": "Juan Pérez",
  "phone": "5551234567",
  "email": "juan.perez@email.com",
  "employee": { "id": 1 }
}
```

**Empleado**
```json
{
  "full_name": "Ana López",
  "phone": "5559876543",
  "email": "ana.lopez@email.com",
  "role": { "id": 1 }
}
```

**Venta**
```json
{
  "vehicleId": 4,
  "customerId": 1,
  "serviceIds": [1, 2]
}
```
> Para el POST de venta debes enviar el id del vehículo, el id del cliente y una lista de ids de servicios asociados a
