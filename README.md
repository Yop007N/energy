```markdown
# Energy

Este proyecto `energy` es un sistema de gestión de energía desarrollado con Spring Boot. El proyecto está estructurado en múltiples módulos que representan diferentes servicios dentro del sistema.

## Módulos del Proyecto

- **cliente-service**: Módulo responsable de la gestión de los clientes.
- **consumo-service**: Módulo para el manejo de datos de consumo energético.
- **contrato-service**: Servicio para la administración de contratos de energía.
- **detalle-facturacion-service**: Módulo que gestiona los detalles de la facturación.
- **region-service**: Servicio para la gestión de las regiones donde se suministra energía.
- **medidor-service**: Módulo que administra los medidores de energía.
- **facturacion-service**: Servicio encargado de la generación y gestión de facturas.
- **potencia-service**: Módulo que gestiona la potencia energética contratada.

## Tecnologías Utilizadas

- **Spring Boot**: Versión 3.2.1
- **Java**: Versión 17
- **Maven**: Para la gestión de dependencias y construcción del proyecto.

## Requisitos

- **Java 17** o superior
- **Maven 3.8** o superior

## Instalación y Configuración

1. **Clonar el repositorio**:
   ```bash
   git clone 
   cd energy
   ```

2. **Construir el proyecto**:
   Ejecuta el siguiente comando para construir todos los módulos:
   ```bash
   mvn clean install
   ```

3. **Ejecutar un módulo específico**:
   Para ejecutar un módulo en particular, por ejemplo `cliente-service`:
   ```bash
   cd cliente-service
   mvn spring-boot:run
   ```


## Endpoints del Servicio de Clientes

### Obtener todos los clientes (GET /clientes):

- **Método:** GET
- **URL:** `http://localhost:8002/clientes`
- **Headers:** No es necesario.
- **Body:** No es necesario.

### Obtener un cliente por ID (GET /clientes/{id}):

- **Método:** GET
- **URL:** `http://localhost:8002/clientes/{id}`
- **Headers:** No es necesario.
- **Body:** No es necesario.

### Crear un nuevo cliente (POST /clientes):

- **Método:** POST
- **URL:** `http://localhost:8002/clientes`
- **Headers:**
  - `Content-Type: application/json`
- **Body:**
  ```json
  {
    "rucCi": "123456789",
    "nombre": "Nombre del Cliente",
    "direccion": "Dirección del Cliente",
    "tipoCliente": "RESIDENCIAL"
  }
  ```

### Actualizar un cliente existente (PUT /clientes/{id}):

- **Método:** PUT
- **URL:** `http://localhost:8002/clientes/{id}`
- **Headers:**
  - `Content-Type: application/json`
- **Body:**
  ```json
  {
    "rucCi": "987654321",
    "nombre": "Nuevo Nombre del Cliente",
    "direccion": "Nueva Dirección del Cliente",
    "tipoCliente": "COMERCIAL"
  }
  ```

### Eliminar un cliente (DELETE /clientes/{id}):

- **Método:** DELETE
- **URL:** `http://localhost:8002/clientes/{id}`
- **Headers:** No es necesario.
- **Body:** No es necesario.



   
