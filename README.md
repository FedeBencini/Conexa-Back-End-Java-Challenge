# **Challenge Conexa - API Star Wars**

![Java](https://img.shields.io/badge/Java-17-blue) ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green) ![License](https://img.shields.io/badge/License-MIT-lightgrey)

Este proyecto es una aplicación BackEnd desarrollada con **Spring Boot** que interactúa con la **Star Wars API (SWAPI)** para obtener información sobre personajes, películas, naves espaciales y vehículos, presentándose de forma paginada, o pudiendo filtrar mediante ID o nombre. Asimismo, cuenta con un sistema de autenticación seguro mediante JWT, que permite proteger los distintos endpoints.

---

## **Tabla de Contenidos**

1. [Tecnologías Utilizadas](#tecnologías-utilizadas)
2. [Funcionamiento del Proyecto](#funcionamiento-del-proyecto)
3. [Instalación y Configuración](#instalación-y-configuración)
4. [Cómo Probar el Proyecto](#cómo-probar-el-proyecto)
5. [Generación de Documentación Javadoc](#generación-de-documentación-javadoc)
6. [Estructura del Proyecto](#estructura-del-proyecto)
7. [Licencia](#licencia)

---

## **Tecnologías Utilizadas**

- **Java 17**: Versión LTS utilizada para el desarrollo.
- **Spring Boot 3.5.5**: Framework para construir aplicaciones backend robustas.
- **Spring Security**: Implementación de autenticación JWT para proteger los endpoints.
- **RestTemplate**: Cliente HTTP utilizado para interactuar con la API de SWAPI.
- **JUnit 5 & Mockito**: Herramientas para realizar pruebas unitarias e integración.
- **Maven**: Gestor de dependencias y construcción del proyecto.
- **Javadoc**: Generación de documentación técnica.

---

## **Funcionamiento del Proyecto**

Este proyecto tiene dos componentes principales:

1. **Autenticación JWT**:
   - Los usuarios deben iniciar sesión para obtener un token JWT.
   - El token se utiliza para acceder a los endpoints protegidos.

2. **Interacción con SWAPI**:
   - El cliente `SwapiClient` realiza solicitudes HTTP a la API de SWAPI para obtener datos sobre:
     - Personajes (`/people`)
     - Películas (`/films`)
     - Naves espaciales (`/starships`)
     - Vehículos (`/vehicles`)
   - Los datos obtenidos se transforman en DTOs y se exponen a través de endpoints REST.

---

## **Instalación y Configuración**

### **Requisitos Previos**

- **Java 17**: Asegúrate de tener instalada esta versión o superior.
- **Maven**: Necesario para compilar y ejecutar el proyecto.
- **Git**: Para clonar el repositorio.

### **Pasos para Instalar**

1. Clona el repositorio:
   ```bash
   git clone https://github.com/FedeBencini/Conexa-Back-End-Java-Challenge.git
   cd challenge-conexa
   ```

2. Compila el proyecto:
   ```bash
   mvn clean install
   ```

3. Ejecuta la aplicación:
   ```bash
   mvn spring-boot:run
   ```

---

## **Cómo Probar el Proyecto**

### **Pruebas Manuales**

1. **Autenticación**:
   - Realiza una solicitud POST al único endpoint público `/auth/login` con las credenciales válidas:
     ```json
     {
       "username": "admin",
       "password": "password"
     }
     ```
   - Guarda el token JWT devuelto en la respuesta.

2. **Endpoints Protegidos**:
   - Usa el token JWT en el encabezado `Authorization` para acceder a los demás endpoints protegidos. Por ejemplo:
     ```bash
     curl -H "Authorization: Bearer <token>" http://localhost:8080/api/people
     ```

### **Pruebas Automatizadas**

1. Ejecuta las pruebas unitarias e integración:
   ```bash
   mvn test
   ```

---

## **Generación de Documentación Javadoc**

Para generar la documentación Javadoc:

1. Ejecuta el siguiente comando:
   ```bash
   mvn javadoc:javadoc
   ```

2. La documentación generada estará disponible en la carpeta `target/site/apidocs`.

3. Abre el archivo `index.html` en tu navegador para explorar la documentación.

---

## **Estructura del Proyecto**

```
challenge-conexa/
├── src/
│   ├── main/
│   │   ├── java/com/FedeB/Challenge_Conexa/
│   │   │   ├── controller/    # Controladores REST
│   │   │   ├── dto/           # DTOs para mapear respuestas
│   │   │   ├── service/       # Servicios de negocio
│   │   │   ├── filter/        # Filtro para validación de tokens JWT
│   │   │   ├── integration/   # Integración con Star Wars API
│   │   │   └── config/        # Configuración de Spring Security y otros
│   │   └── resources/
│   │       └── application.yml # Configuración de la aplicación
│   └── test/
│       ├── java/              # Pruebas unitarias e integración
├── pom.xml                    # Archivo de configuración de Maven
└── README.md                  # Documentación del proyecto
```

---

## **Licencia**

Este proyecto está bajo la licencia [MIT](LICENSE). Puedes usar, modificar y distribuir este código de acuerdo con los términos de la licencia.
