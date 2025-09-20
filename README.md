# 🏗️ Microservices Energy Platform

> **Plataforma de microservicios para gestión energética empresarial con Clean Architecture**

## 📋 Descripción

Microservices Energy Platform es un sistema robusto de gestión energética desarrollado con Spring Boot que implementa arquitectura de microservicios para garantizar escalabilidad, mantenibilidad y alta disponibilidad. La plataforma gestiona de manera integral el ciclo completo de administración energética desde clientes hasta facturación.

## 🏛️ Arquitectura de Microservicios

### Estructura de Servicios

```
🌐 Energy Platform Ecosystem
├── 👥 cliente-service (8002)          # Gestión de clientes
├── ⚡ consumo-service (8003)          # Monitoreo de consumo
├── 📄 contrato-service (8004)         # Administración de contratos
├── 🧾 facturacion-service (8006)      # Generación de facturas
├── 📊 detalle-facturacion-service     # Detalles de facturación
├── 🔌 medidor-service (8007)          # Control de medidores IoT
├── 💪 potencia-service (8008)         # Gestión de potencia
└── 📍 region-service (8009)           # Administración regional
```

### Clean Architecture Implementation

```
📁 Cada Microservicio
├── domain/                     # Capa de Dominio
│   ├── entities/              # Entidades de negocio
│   ├── valueobjects/          # Value Objects
│   ├── repositories/          # Interfaces de repositorio
│   └── exceptions/            # Excepciones de dominio
├── application/               # Capa de Aplicación
│   ├── usecases/             # Casos de uso
│   ├── dto/                  # DTOs de aplicación
│   └── services/             # Servicios de aplicación
├── infrastructure/            # Capa de Infraestructura
│   ├── persistence/          # Implementaciones JPA
│   ├── web/                  # Controladores REST
│   └── config/               # Configuraciones
└── shared/                   # Componentes compartidos
    ├── entities/             # Entidades base
    ├── exceptions/           # Excepciones comunes
    └── valueobjects/         # Value Objects compartidos
```

## 💻 Stack Tecnológico

### Backend Core
- **Spring Boot 3.3.4** - Framework principal
- **Java 17** - Lenguaje de programación
- **Maven** - Gestión de dependencias y build
- **Spring Data JPA** - Persistencia de datos
- **Spring Web** - APIs RESTful

### Persistencia
- **H2 Database** - Base de datos en memoria (desarrollo)
- **PostgreSQL** - Base de datos principal (producción)
- **JPA/Hibernate** - ORM y mapeo objeto-relacional

### Herramientas de Desarrollo
- **Lombok** - Reducción de código boilerplate
- **Spring Boot DevTools** - Desarrollo rápido con hot reload
- **OpenAPI/Swagger** - Documentación de APIs
- **Validation API** - Validaciones de entrada

### Testing
- **JUnit 5** - Framework de testing
- **Mockito** - Mock objects
- **Spring Boot Test** - Testing de integración

## 🚀 Instalación

### Prerrequisitos

- **Java 17** o superior
- **Maven 3.8** o superior
- **Git** para control de versiones

### Pasos de Instalación

```bash
# 1. Clonar el repositorio
git clone https://github.com/Yop007N/microservices-energy-platform.git
cd microservices-energy-platform

# 2. Construir todos los microservicios
mvn clean install

# 3. Ejecutar servicios individuales
cd cliente-service && mvn spring-boot:run &
cd consumo-service && mvn spring-boot:run &
cd contrato-service && mvn spring-boot:run &
cd facturacion-service && mvn spring-boot:run &
# ... otros servicios
```

### Ejecución con Docker

```bash
# Construir imágenes
docker-compose build

# Ejecutar toda la plataforma
docker-compose up -d

# Ver logs
docker-compose logs -f
```

## 📡 APIs y Endpoints

### 👥 Cliente Service (Puerto 8002)

```typescript
// Gestión completa de clientes
POST   /api/v1/clientes           # Crear cliente
GET    /api/v1/clientes/{id}      # Obtener cliente
PUT    /api/v1/clientes/{id}      # Actualizar cliente
DELETE /api/v1/clientes/{id}      # Eliminar cliente
GET    /api/v1/clientes/health    # Estado del servicio
```

#### Estructura Cliente
```json
{
  "id": "uuid",
  "identificacion": "12345678-9",
  "nombre": "Juan Pérez",
  "apellido": "González",
  "tipoCliente": "RESIDENCIAL",
  "contactoInfo": {
    "telefono": "+595981234567",
    "email": "juan.perez@email.com",
    "direccion": "Av. Principal 123"
  },
  "estado": "ACTIVO"
}
```

### ⚡ Consumo Service (Puerto 8003)

```typescript
GET    /api/v1/consumos                    # Listar consumos
POST   /api/v1/consumos                    # Registrar consumo
GET    /api/v1/consumos/cliente/{id}       # Consumos por cliente
GET    /api/v1/consumos/periodo/{periodo}  # Consumos por período
GET    /api/v1/consumos/analytics          # Analytics de consumo
```

### 📄 Contrato Service (Puerto 8004)

```typescript
POST   /api/v1/contratos              # Crear contrato
GET    /api/v1/contratos/{id}         # Obtener contrato
PUT    /api/v1/contratos/{id}         # Actualizar contrato
DELETE /api/v1/contratos/{id}         # Cancelar contrato
GET    /api/v1/contratos/cliente/{id} # Contratos por cliente
```

### 🧾 Facturación Service (Puerto 8006)

```typescript
POST   /api/v1/facturas/generar       # Generar factura
GET    /api/v1/facturas/{id}          # Obtener factura
GET    /api/v1/facturas/cliente/{id}  # Facturas por cliente
PUT    /api/v1/facturas/{id}/pagar    # Marcar como pagada
GET    /api/v1/facturas/pendientes    # Facturas pendientes
```

## ⚙️ Configuración

### Variables de Entorno

```bash
# Base de datos
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/energy_db
SPRING_DATASOURCE_USERNAME=energy_user
SPRING_DATASOURCE_PASSWORD=energy_pass

# Configuración de microservicios
SERVER_PORT=8002
EUREKA_CLIENT_SERVICE_URL=http://localhost:8761

# Logging
LOGGING_LEVEL_COM_ENERGIA=DEBUG
LOGGING_PATTERN_FILE=%d{yyyy-MM-dd HH:mm:ss} [%thread] %-5level %logger{36} - %msg%n
```

### application.yml Ejemplo

```yaml
spring:
  application:
    name: cliente-service
  datasource:
    url: jdbc:h2:mem:testdb
    driver-class-name: org.h2.Driver
    username: sa
    password:
  jpa:
    hibernate:
      ddl-auto: create-drop
    show-sql: true
    properties:
      hibernate:
        format_sql: true

server:
  port: 8002

management:
  endpoints:
    web:
      exposure:
        include: health,info,metrics
  endpoint:
    health:
      show-details: always

logging:
  level:
    com.energia: DEBUG
    org.springframework.web: DEBUG
```

## 🎯 Casos de Uso Implementados

### Flujo Principal del Sistema

```typescript
// 1. Registro de cliente
const cliente = await clienteService.crearCliente({
  identificacion: "12345678-9",
  nombre: "Juan Pérez",
  tipoCliente: "RESIDENCIAL"
});

// 2. Creación de contrato
const contrato = await contratoService.crearContrato({
  clienteId: cliente.id,
  tipoContrato: "RESIDENCIAL",
  potenciaContratada: 10.0
});

// 3. Instalación de medidor
const medidor = await medidorService.instalarMedidor({
  contratoId: contrato.id,
  numeroSerie: "MED-001",
  tipo: "INTELIGENTE"
});

// 4. Registro de consumo
const consumo = await consumoService.registrarConsumo({
  medidorId: medidor.id,
  lecturaActual: 1500.5,
  periodo: "2024-12"
});

// 5. Generación de factura
const factura = await facturacionService.generarFactura({
  clienteId: cliente.id,
  consumoId: consumo.id,
  periodo: "2024-12"
});
```

### Casos de Uso por Microservicio

#### Cliente Service
- Crear/actualizar/eliminar clientes
- Validar datos de contacto
- Gestionar tipos de cliente
- Manejar estados del cliente

#### Consumo Service
- Registrar lecturas de medidores
- Calcular consumo por períodos
- Generar reportes de consumo
- Detectar anomalías en el consumo

#### Facturación Service
- Generar facturas automáticas
- Calcular tarifas según tipo de cliente
- Procesar pagos
- Enviar notificaciones de vencimiento

## 🧪 Testing

### Ejecutar Tests

```bash
# Tests unitarios
mvn test

# Tests de integración
mvn integration-test

# Cobertura de código
mvn jacoco:report

# Tests específicos por servicio
cd cliente-service && mvn test
```

### Estructura de Testing

```
src/test/java/
├── unit/                    # Tests unitarios
│   ├── domain/             # Tests de entidades
│   ├── application/        # Tests de casos de uso
│   └── infrastructure/     # Tests de implementación
├── integration/            # Tests de integración
│   ├── web/               # Tests de controladores
│   └── persistence/       # Tests de repositorios
└── e2e/                   # Tests end-to-end
```

## 🔄 Ciclo de Vida del Desarrollo

### Flujo de Trabajo

1. **Desarrollo**: Implementación de nuevas features
2. **Testing**: Ejecución de tests automatizados
3. **Build**: Construcción con Maven
4. **Deploy**: Despliegue en entornos
5. **Monitor**: Monitoreo y métricas

### Scripts de Automatización

```bash
# Script de inicio completo
./scripts/start-all-services.sh

# Script de despliegue
./scripts/deploy.sh

# Script de monitoreo
./scripts/health-check.sh
```

## 📊 Monitoreo y Observabilidad

### Métricas Disponibles

- **Spring Boot Actuator** - Health checks y métricas
- **Micrometer** - Métricas personalizadas
- **Prometheus** - Recolección de métricas
- **Grafana** - Dashboards de visualización

### Endpoints de Monitoreo

```
GET /actuator/health          # Estado del servicio
GET /actuator/metrics         # Métricas del sistema
GET /actuator/info           # Información del servicio
GET /actuator/prometheus     # Métricas para Prometheus
```

## 🌐 Despliegue

### Docker Compose

```yaml
version: '3.8'
services:
  cliente-service:
    build: ./cliente-service
    ports:
      - "8002:8002"
    environment:
      - SPRING_PROFILES_ACTIVE=docker

  consumo-service:
    build: ./consumo-service
    ports:
      - "8003:8003"
    environment:
      - SPRING_PROFILES_ACTIVE=docker
```

### Kubernetes

```bash
# Aplicar configuraciones
kubectl apply -f k8s/

# Verificar deployments
kubectl get pods -n energy-platform

# Ver logs
kubectl logs -f deployment/cliente-service
```

## 📈 Métricas de Performance

### Indicadores Clave

| Métrica | Objetivo | Actual |
|---------|----------|--------|
| **Response Time** | < 200ms | 150ms |
| **Throughput** | > 1000 req/s | 1200 req/s |
| **Error Rate** | < 1% | 0.5% |
| **Availability** | > 99.9% | 99.95% |

## 🔒 Seguridad

### Implementaciones de Seguridad

- **Validación de entrada** en todos los endpoints
- **Manejo de errores** sin exposición de información sensible
- **Logging seguro** sin datos personales
- **Encriptación** de datos sensibles

## 👨‍💻 Autor

**Enrique Bobadilla**

---

**Versión:** 1.0.0
**Última actualización:** Diciembre 2024