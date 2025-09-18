# Energy Management Platform ⚡

> Sistema completo de gestión energética desarrollado con Spring Boot y arquitectura de microservicios

## 📋 Descripción

Energy Management Platform es un sistema robusto de gestión energética que permite administrar clientes, contratos, facturación, y monitoreo de consumo eléctrico. El proyecto está construido con arquitectura de microservicios para garantizar escalabilidad y mantenibilidad.

## 🏗️ Arquitectura de Microservicios

### 📊 Servicios Disponibles

| Servicio | Puerto | Descripción |
|----------|--------|-------------|
| **cliente-service** | 8002 | Gestión de clientes y tipos de usuario |
| **consumo-service** | 8003 | Monitoreo y análisis de consumo energético |
| **contrato-service** | 8004 | Administración de contratos de energía |
| **detalle-facturacion-service** | 8005 | Gestión de detalles de facturación |
| **facturacion-service** | 8006 | Generación y procesamiento de facturas |
| **medidor-service** | 8007 | Administración de medidores inteligentes |
| **potencia-service** | 8008 | Gestión de potencia contratada |
| **region-service** | 8009 | Administración de regiones de suministro |

## 🛠️ Stack Tecnológico

### Backend
- **Spring Boot 3.2.1** - Framework principal
- **Java 17** - Lenguaje de programación
- **Maven** - Gestión de dependencias
- **Spring Data JPA** - Persistencia de datos
- **Spring Web** - APIs RESTful

### Base de Datos
- **H2 Database** (desarrollo)
- **PostgreSQL** (producción recomendada)
- **MySQL** (compatible)

### Herramientas
- **Spring Boot DevTools** - Desarrollo rápido
- **Lombok** - Reducción de código boilerplate
- **MapStruct** - Mapeo de objetos

## 🚀 Instalación y Configuración

### Prerrequisitos
- Java 17 o superior
- Maven 3.8 o superior
- Git

### Instalación

```bash
# Clonar el repositorio
git clone https://github.com/Yop007N/energy.git
cd energy

# Construir todos los microservicios
mvn clean install

# Ejecutar todos los servicios (en terminales separadas)
./start-all-services.sh
```

### Ejecución Individual de Servicios

```bash
# Cliente Service
cd cliente-service && mvn spring-boot:run

# Consumo Service
cd consumo-service && mvn spring-boot:run

# Otros servicios...
cd [nombre-servicio] && mvn spring-boot:run
```

## 📡 API Endpoints

### 👥 Cliente Service (Puerto 8002)

```http
GET    /clientes           # Obtener todos los clientes
GET    /clientes/{id}      # Obtener cliente por ID
POST   /clientes           # Crear nuevo cliente
PUT    /clientes/{id}      # Actualizar cliente
DELETE /clientes/{id}      # Eliminar cliente
```

#### Ejemplo de Cliente
```json
{
  "rucCi": "12345678-9",
  "nombre": "Juan Pérez",
  "direccion": "Av. Principal 123",
  "tipoCliente": "RESIDENCIAL"
}
```

### ⚡ Consumo Service (Puerto 8003)

```http
GET    /consumos                    # Obtener consumos
GET    /consumos/cliente/{id}       # Consumos por cliente
POST   /consumos                    # Registrar consumo
GET    /consumos/periodo/{periodo}  # Consumos por período
```

### 📋 Contrato Service (Puerto 8004)

```http
GET    /contratos              # Listar contratos
POST   /contratos              # Crear contrato
PUT    /contratos/{id}         # Actualizar contrato
DELETE /contratos/{id}         # Cancelar contrato
```

### 📊 Facturación Service (Puerto 8006)

```http
GET    /facturas               # Obtener facturas
POST   /facturas/generar       # Generar nueva factura
GET    /facturas/cliente/{id}  # Facturas por cliente
PUT    /facturas/{id}/pagar    # Marcar como pagada
```

## 🔧 Configuración

### Variables de Entorno

```bash
# Base de datos
DB_HOST=localhost
DB_PORT=5432
DB_NAME=energy_db
DB_USER=energy_user
DB_PASSWORD=energy_pass

# Configuración de servicios
SERVER_PORT=8002
EUREKA_URL=http://localhost:8761
```

### application.yml (Ejemplo)

```yaml
spring:
  application:
    name: cliente-service
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true

server:
  port: 8002

logging:
  level:
    com.energy: DEBUG
```

## 🏗️ Estructura del Proyecto

```
energy/
├── cliente-service/
│   ├── src/main/java/
│   │   ├── controller/    # Controladores REST
│   │   ├── service/       # Lógica de negocio
│   │   ├── repository/    # Acceso a datos
│   │   ├── model/         # Entidades JPA
│   │   └── dto/           # Objetos de transferencia
│   └── pom.xml
├── consumo-service/
├── contrato-service/
├── facturacion-service/
├── medidor-service/
├── potencia-service/
├── region-service/
├── detalle-facturacion-service/
└── pom.xml                # POM padre
```

## 🔄 Flujo de Trabajo

1. **Registro de Cliente** → Cliente Service
2. **Creación de Contrato** → Contrato Service
3. **Instalación de Medidor** → Medidor Service
4. **Registro de Consumo** → Consumo Service
5. **Generación de Factura** → Facturación Service
6. **Procesamiento de Pago** → Detalle Facturación Service

## 📊 Características Principales

### ✨ Funcionalidades

- **🏠 Gestión de Clientes**: CRUD completo con tipos de cliente
- **📄 Contratos Inteligentes**: Administración automatizada de contratos
- **⚡ Monitoreo en Tiempo Real**: Seguimiento de consumo energético
- **🧾 Facturación Automática**: Generación y envío de facturas
- **📍 Gestión Regional**: Administración por zonas geográficas
- **🔌 Control de Medidores**: Gestión de dispositivos IoT
- **💪 Análisis de Potencia**: Optimización de potencia contratada

### 🎯 Ventajas del Sistema

- **Escalabilidad**: Arquitectura de microservicios
- **Mantenibilidad**: Separación clara de responsabilidades
- **Flexibilidad**: Fácil integración de nuevos servicios
- **Eficiencia**: Optimización de recursos energéticos

## 🧪 Testing

```bash
# Ejecutar tests
mvn test

# Tests de integración
mvn integration-test

# Cobertura de código
mvn jacoco:report
```

## 🌐 Despliegue

### Docker (Recomendado)

```bash
# Construir imágenes
docker-compose build

# Ejecutar todos los servicios
docker-compose up -d

# Ver logs
docker-compose logs -f
```

### Kubernetes

```bash
# Aplicar configuraciones
kubectl apply -f k8s/

# Verificar deployments
kubectl get pods
```

## 📈 Monitoreo

- **Spring Boot Actuator** - Health checks y métricas
- **Prometheus** - Recolección de métricas
- **Grafana** - Visualización de datos
- **ELK Stack** - Logs centralizados

## 🤝 Contribución

1. Fork el proyecto
2. Crea tu branch (`git checkout -b feature/NuevaFuncionalidad`)
3. Commit tus cambios (`git commit -m 'Add: Nueva funcionalidad'`)
4. Push al branch (`git push origin feature/NuevaFuncionalidad`)
5. Abre un Pull Request

## 📝 Licencia

Este proyecto está bajo la Licencia MIT. Ver `LICENSE` para más detalles.

## 👨‍💻 Autor

**Enrique B. (Yop007N)**
- GitHub: [@Yop007N](https://github.com/Yop007N)
- Especialización: IoT y Eficiencia Energética

## 🔗 Enlaces Relacionados

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [Java 17 Documentation](https://docs.oracle.com/en/java/javase/17/)
- [Maven Documentation](https://maven.apache.org/guides/)

---

⚡ Construyendo el futuro de la gestión energética inteligente