Caso elegido: CinePlus
1. Caso elegido y alcance

Alcance EP3:
En este proyecto se trabajó en el diseño e interfaz, la navegación entre pantallas, el manejo de estado y persistencia, el uso de recursos nativos como cámara y mapa, además del consumo de una API externa para conectar la app con un backend (backend elegido del anexo A).

2. Requisitos y ejecución

Tener Android Studio con Kotlin y Jetpack Compose.

Instalación:
Abrir el proyecto en Android Studio y clonar el repositorio. Luego presionar el boton de play y listo!

3. Arquitectura y flujo

Estructura de carpetas:
ui/screens para las pantallas, navigation para las rutas, viewmodel para la lógica y theme para los colores.

Gestión de estado:
Uso de ViewModel y StateFlow para observar cambios y actualizar la interfaz en tiempo real.

Navegación:
Implementada con NavController, manejando rutas como login, registro y home/cartelera.

4. Funcionalidades

Pantalla de registro validado con conexión a API.

Login funcional con validación básica.

HomeScreen con cartelera de películas (mock y luego API).

Modo oscuro activable desde la pantalla principal.

Recursos nativos: integración de cámara y mapa.

Animaciones suaves para transiciones.

Consumo de API (Xano) para manejar registro y autenticación.

5. Endpoints

Base URL: https://x8ki-letl-twmt.n7.xano.io/api:Rfm_61dW

Método	Ruta	Body	Respuesta
POST	/auth/signup	{ email, password, name }	201 → { authToken, user: { id, email, name } }
POST	/auth/login	{ email, password }	200 → { authToken, user: { id, email, name } }
GET	/auth/me	- (requiere header Authorization)	200 → { id, email, name }

6. User flows

El flujo principal es el siguiente:

El usuario abre la app y ve la pantalla de login.

Puede registrarse si no tiene cuenta (pantalla de registro).

Una vez autenticado, se dirige al HomeScreen, donde ve la cartelera.

Desde allí puede cambiar al modo oscuro, acceder a la cámara o mapa, y explorar las funciones del app.
