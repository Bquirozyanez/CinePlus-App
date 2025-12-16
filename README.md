# Proyecto CinePlus

Grupo 3
Integrantes:
Benjamín Quiroz y Gabriel Cárdenas

## Nombre de la aplicación: CinePlus

CinePlus es una aplicación móvil desarrollada en Android Studio, utilizando Kotlin y Jetpack Compose, cuyo objetivo es simular una plataforma de cine moderna, incorporando autenticación real, consumo de una API propia y funcionalidades nativas del dispositivo como cámara y persistencia local. Durante el desarrollo, el proyecto fue evolucionando tanto a nivel técnico como conceptual: comenzamos utilizando Xano como backend y posteriormente migramos a un backend propio desarrollado en NestJS, el cual fue desplegado en Render, logrando una conexión real entre la app móvil y una API funcional. La aplicación permite al usuario registrarse, iniciar sesión, visualizar información resumida de su perfil y acceder a una cartelera de películas, todo dentro de un flujo de navegación claro y coherente.

## Funcionalidades
La aplicación CinePlus cuenta con las siguientes funcionalidades implementadas:
- Registro de usuarios conectado a una API propia, con validaciones de nombre, correo y contraseña.
- Inicio de sesión real utilizando autenticación con JWT, manejo de errores HTTP y control de estados.
- Resumen del perfil del usuario, donde se visualizan los datos ingresados.
- Cartelera de películas, mostrando información básica como título, imagen y duración.
- Recuperación de contraseña, mediante una pantalla dedicada que valida el correo del usuario (flujo visual).
- Modificación de datos del usuario, permitiendo editar información desde la aplicación.
- Modo oscuro, persistente mediante DataStore, que se mantiene activo incluso al cerrar la app.
- Uso de cámara con lector QR, implementado con CameraX.
- Integración de mapa nativo, utilizando Google Maps Compose, demostrando el uso de recursos nativos del dispositivo.
- Navegación completa entre pantallas usando Navigation Compose.
- Tests unitarios, enfocados en la lógica de validación y ViewModels.



## Endpoints usados (propios y externos)
Backend propio (NestJS – Render)

La aplicación se conecta a un backend propio desplegado en Render: https://cineplus-api-oi1n.onrender.com/api/

Endpoints utilizados:

Función	Método	Endpoint	Descripción
Registro	POST	/auth/register	Crea un nuevo usuario
Login	POST	/auth/login	Retorna un token JWT
Perfil	GET	/auth/profile	Obtiene datos del usuario autenticado

Los errores son manejados desde Retrofit mediante códigos HTTP 400–500.

Servicios externos:
- Google Maps API → para la funcionalidad de mapa nativo.
- CameraX → para el uso de cámara y escaneo de códigos QR.



## Instrucciones para ejecutar el proyecto
### Requisitos:
- Android Studio Ladybug o superior
- Kotlin
- Jetpack Compose
- Conexión a internet

### Pasos:

1. Clonar el repositorio desde GitHub.
2. Abrir el proyecto en Android Studio.
3. Sincronizar Gradle.
4. Presionar Run.

La aplicación se conectará automáticamente al backend desplegado en Render. El backend debe estar activo para que el registro y login funcionen correctamente.



## APK firmado y archivo .jks

- El APK firmado se encuentra incluido dentro del repositorio, en la carpeta correspondiente a releases.
- El archivo .jks fue utilizado para firmar la aplicación durante el proceso de generación del APK.



## Código fuente de microservicios y app móvil
App móvil (Android – Kotlin)
El código fuente de la aplicación móvil se encuentra en este repositorio. Incluye:
- UI con Jetpack Compose
- ViewModels
- Navegación
- Consumo de API
- Persistencia local
- Recursos nativos (cámara y mapa)
- Microservicio (Backend – NestJS)

El backend fue desarrollado como un microservicio independiente en NestJS:

Repositorio Backend CinePlus API
https://github.com/Bquirozyanez/cineplus-api

Este microservicio gestiona:
- Registro de usuarios
- Autenticación
- Generación y validación de JWT
- Endpoints REST consumidos por la app móvil






