package com.example.cineplus.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cineplus.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ModificarScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    // Si tu VM tiene cargarUsuario(), puedes descomentar esto para asegurarte que trae datos.
    LaunchedEffect(Unit) {
        runCatching { viewModel.cargarUsuario() }
    }

    val user by viewModel.user.collectAsState()

    // Campos editables SOLO locales (no se guardan)
    var nombreLocal by remember { mutableStateOf("") }
    var emailLocal by remember { mutableStateOf("") }
    var passLocal by remember { mutableStateOf("") }

    // Para cargar una vez desde user -> locals
    LaunchedEffect(user) {
        if (nombreLocal.isBlank() && emailLocal.isBlank() && passLocal.isBlank()) {
            nombreLocal = user.name.ifBlank { "" }
            emailLocal = user.email.ifBlank { "" }
            passLocal = user.password.ifBlank { "" }
        }
    }

    var showPassword by remember { mutableStateOf(false) }

    // Feedback animado
    var showMessage by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Datos de usuario") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        },
        containerColor = Color(0xFFEAF0FF)
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            
            AnimatedVisibility(
                visible = showMessage,
                enter = slideInVertically { it / 2 } + fadeIn(),
                exit = slideOutVertically { it / 2 } + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "¡Datos modificados!",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }

            Text(
                text = "Información guardada",
                color = Color(0xFF3F51B5),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = if (nombreLocal.isBlank()) "" else nombreLocal,
                        onValueChange = { nombreLocal = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("(Sin nombre registrado)") }
                    )

                    OutlinedTextField(
                        value = emailLocal,
                        onValueChange = { emailLocal = it },
                        label = { Text("Usuario / Email") },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("(Sin email)") }
                    )

                    OutlinedTextField(
                        value = passLocal,
                        onValueChange = { passLocal = it },
                        label = { Text("Contraseña") },
                        modifier = Modifier.fillMaxWidth(),
                        visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = { showPassword = !showPassword }) {
                                Icon(
                                    imageVector = if (showPassword) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = if (showPassword) "Ocultar" else "Mostrar"
                                )
                            }
                        },
                        placeholder = { Text("(Sin contraseña)") }
                    )

                    Button(
                        onClick = {
                           
                            showMessage = true
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp)
                    ) {
                        Text("Guardar cambios", fontWeight = FontWeight.Bold)
                    }

                    Spacer(Modifier.height(12.dp))

                    Button(
                        onClick = {
                            viewModel.cerrarSesion()
                            navController.navigate("profile") {
                                popUpTo(0)
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp),
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F))
                    ) {
                        Text("Cerrar sesión", fontWeight = FontWeight.Bold, color = Color.White)
                    }

                    // Apagar el mensaje después de 1.5s
                    LaunchedEffect(showMessage) {
                        if (showMessage) {
                            delay(1500)
                            showMessage = false
                            // Si quieres que al "guardar" se resetee a lo guardado real:
                            // nombreLocal = user.name
                            // emailLocal = user.email
                            // passLocal = user.password
                        }
                    }
                }
            }
        }
    }
}
