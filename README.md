# 🎓 Portafolio Académico de Java

Sistema web full-stack para gestionar y mostrar automáticamente un portafolio académico de Java, compuesto por un **Dashboard Administrativo** (CMS) y un **Portafolio Público**. Todo el contenido mostrado en el portafolio público proviene 100% de la base de datos — no hay datos estáticos ni de ejemplo.

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3.3.4 (Spring MVC, Spring Data JPA, Spring Security)
- Thymeleaf
- MySQL
- HTML5 / CSS3 / JavaScript
- Maven

## 📁 Arquitectura

Arquitectura MVC en capas:

```
entity      → Usuario, Perfil, Semana, Actividad (+ enums)
repository  → Interfaces Spring Data JPA
service     → Lógica de negocio (Semana, Actividad, Perfil, archivos)
controller  → Controladores MVC (admin + público)
config      → Seguridad, recursos estáticos, converters
templates   → Vistas Thymeleaf (admin/ y public/)
static      → CSS / JS
```

**Relaciones:**
- `Usuario 1—1 Perfil`
- `Semana 1—N Actividad`

## 🚀 Puesta en marcha

### 1. Crear la base de datos

```sql
CREATE DATABASE portafolio_academico CHARACTER SET utf8mb4;
```

(La aplicación también puede crearla sola gracias a `createDatabaseIfNotExist=true`, pero el usuario de MySQL debe tener permisos).

### 2. Configurar credenciales

Edita `src/main/resources/application.properties` si tu usuario/contraseña de MySQL son distintos a `root` / `root`:

```properties
spring.datasource.username=root
spring.datasource.password=root
```

### 3. Ejecutar el proyecto

```bash
mvn spring-boot:run
```

La aplicación arrancará en **http://localhost:8080**

- Portafolio público: `http://localhost:8080/`
- Dashboard administrativo: `http://localhost:8080/admin/login`

### 4. Iniciar sesión

En el primer arranque se crea automáticamente un usuario administrador:

| Usuario | Contraseña |
|---------|------------|
| `admin` | `admin123` |

⚠️ Cámbiala luego de tu primer ingreso (puedes actualizar la fila en la tabla `usuarios`, la contraseña se guarda cifrada con BCrypt).

## ✨ Funcionalidades

### Dashboard administrativo (`/admin/**`, requiere login)
- Estadísticas generales (semanas, actividades, publicadas, pendientes)
- CRUD completo de **Semanas** (número, título, descripción, imagen de portada, fecha, estado, orden)
- CRUD completo de **Actividades** vinculadas a una semana (tipo, imagen, PDF, enlace externo, código Java, estado, orden)
- Publicar / despublicar semanas y actividades con un clic
- Gestión de **Perfil** (foto, carrera, instituto, curso, docente, descripción, redes sociales)
- Menú lateral responsive (hamburguesa en móvil)
- Diseño elegante: rosa palo, lila, lavanda, dorado sutil, glassmorphism moderado, microanimaciones

### Portafolio público (`/`)
- Muestra automáticamente el perfil y todas las semanas/actividades **publicadas**
- Página de detalle por semana, con sus actividades
- Página de detalle por actividad (descripción, imagen, PDF, enlace, código Java)
- 100% responsive

## 🔐 Seguridad

- Spring Security con `UserDetailsService` personalizado
- Contraseñas cifradas con BCrypt
- Todas las rutas `/admin/**` (excepto `/admin/login`) requieren rol `ADMIN`
- CSRF activo (Thymeleaf inyecta el token automáticamente en los formularios)
- El portafolio público y los recursos estáticos son de acceso libre

## 📤 Archivos subidos

Las imágenes y PDFs se guardan en la carpeta `uploads/` (fuera del classpath) y se sirven mediante `/uploads/**`. Esta carpeta se crea automáticamente al iniciar la aplicación.

## 📝 Notas

- `spring.jpa.hibernate.ddl-auto=update` crea/actualiza las tablas automáticamente; no necesitas scripts SQL manuales.
- No hay datos de ejemplo de semanas o actividades: todo lo que veas en el portafolio público es lo que tú registres desde el dashboard.
