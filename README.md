# Caso elegido: Proyecto CinePlus

Este proyecto corresponde al desarrollo de una app móvil creada en Android Studio (Kotlin + Jetpack Compose) que permite:
- Registrar usuarios
- Iniciar sesión
- Visualizar un resumen
- Mostrar una cartelera de películas
- Usar cámara (QR)
- Implementar modo oscuro
- Conectarse a un backend real alojado en Render

Durante el desarrollo experimentamos una evolución importante: pasamos de usar Xano a un backend NestJS, desplegado en Render.
La app quedó totalmente conectada a una API real, con requests POST/GET funcionando, validaciones completas y un flujo de navegación sólido.



## 1. Caso elegido y alcance

Alcance EP3:
En esta entrega trabajamos principalmente en el diseño de las pantallas principales, siendo estas:
- Login (ProfileScreen)
- Registro (RegisterScreen)
- Resumen (ResumenScreen)
- Cartelera (HomeScreen)

Funcionalidades principales desarrolladas:
✔ Navegación entre pantallas con Navigation Compose
✔ Registro de usuario → conectado a API propia (NestJS + Render)
✔ Inicio de sesión real (JWT, validaciones, errores HTTP)
✔ Resumen de datos ingresados
✔ Listado de películas en Home
✔ Modo oscuro persistente con DataStore
✔ Cámara con QR Scanner
✔ Se comenzó la implementación del mapa (pero no se alcanzó a finalizar)

Además, se realizaron tests unitarios sobre la lógica de validación de usuario y ViewModels para cumplir con la rúbrica.



## 2. Requisitos y ejecución

### Requisitos
- Android Studio Ladybug+
- Kotlin
- Jetpack Compose
- Conexión a internet (la app depende de la API en Render)

### Cómo ejecutarlo

1. Clonar el repositorio:
https://github.com/Bquirozyanez/CinePlus2-Test-OFICIAL.git
2. Abrir el proyecto en Android Studio
3. Presionar ▶ Run
4. La app se conectará automáticamente al backend en Render (Este debe estar ejecutandose previamente en Render)



## 3. Arquitectura y flujo

- ui/screen: Contiene todas las pantallas visibles para el usuario.
- navigation: Se encarga de movernos entre pantallas.
- data/remote: Conecta la app con la API de Xano.
- repository: Une los datos con la lógica de la app.
- viewmodel: Controla lo que pasa en cada pantalla y guarda su estado.
- data: Guarda cosas locales, como la preferencia del modo oscuro.


app/src/main/java/com/example/cineplus/
│
├── data/ 
│   ├── remote/ 
│   │   ├── dto/ (modelos usados por la API)
│   │   │   ├── LoginRequest.kt
│   │   │   ├── LoginResponse.kt
│   │   │   ├── RegisterRequest.kt
│   │   │   └── RegisterResponse.kt
│   │   ├── ApiService.kt 
│   │   ├── RetrofitClient.kt 
│   │   └── DarkModeDataStore.kt 
│   └── model/ 
│
├── navigation/ 
│   ├── AppNavigation.kt 
│   ├── NavigationEvent.kt
│   └── Screen.kt 
│
├── repository/ 
│   └── AuthRepository.kt 
│
├── ui/ 
│   ├── screens/ (todas las pantallas visibles)
│   │   ├── HomeScreen.kt
│   │   ├── ProfileScreen.kt
│   │   ├── RegisterScreen.kt
│   │   └── ResumenScreen.kt
│   ├── state/
│   │   └── UsuarioUiState.kt 
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   └── utils/
│       └── WindowSizeUtils.kt 
│
├── viewmodel/ 
│   ├── DarkModeViewModel.kt
│   ├── LoginViewModel.kt
│   ├── MainViewModel.kt
│   ├── RegisterViewModel.kt
│   └── UsuarioViewModel.kt
│
└── MainActivity.kt

### Flujo interno de datos
Pantalla → ViewModel → Repository → API (Render) → Repository → ViewModel → UI



## 4. Funcionalidades

### Registro
- Valida nombre, correo y contraseña.
- Envía datos al endpoint /auth/register.
- Maneja errores:
    - contraseña corta
    - email ya registrado
    - campos vacíos
- Navega al resumen si la API responde correctamente.

### Inicio de sesión
- Envia email y password a /auth/login.
- Guarda el token JWT.
- Si es correcto → va a HomeScreen.
- Si falla → se muestra error.

### Home / Cartelera
- Películas mockeadas para la presentación.
- Título, imagen y duración.

### Modo oscuro
- Guardado con DataStore → persiste incluso si se cierra la app.

### Cámara (QR)
- Implementada con CameraX.
- Abre cámara y detecta códigos QR.



## 5. Endpoints

La app actualmente se conecta al backend desplegado en Render: **https://cineplus-api-oi1n.onrender.com/api/**

Endpoints reales usados:

| Función  | Método   |    Endpoint    | Descripción |
|----------|----------|----------------|--------------|
| Registro | **POST** | /auth/register | Crea un usuario nuevo
| Login    | **POST** | /auth/login    | Devuelve token JWT si las credenciales son correctas
| Perfil   | **GET**  | /auth/profile  | Obtiene los datos del usuario usando el token

Los errores se manejan desde Retrofit con .isSuccessful y códigos 400–500.



## 6. User flows

1.Usuario abre la app
2. Ve pantalla de Login
3. Si no tiene cuenta → va a Registro
4. Ingreso de datos → aparece Resumen
5. Luego pasa al HomeScreen con la cartelera
6. Desde ahí puede activar modo oscuro y usar la cámara



## 7. Tests unitarios

Realizamos 7 tests unitarios usando JUnit.
Se trabajaron principalmente sobre:
- *UsuarioValidator*
- *RegisterViewModel*
- *LoginViewModel*

### Ejemplos de tests creados:

1. Validar que el nombre no sea vacío
2. Validar formato correcto del correo
3. Contraseña con mínimo 6 caracteres
4. Registro fallido si hay campos vacíos
5. Registro exitoso con datos válidos
6. Login fallido si la API devuelve error
7. Login exitoso si la API entrega token

Todos los tests corrieron correctamente en Android Studio.











