package com.example.cineplus.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.viewmodel.RegisterViewModel
import com.example.cineplus.viewmodel.UsuarioViewModel
import kotlinx.coroutines.delay

@Composable
fun RegisterScreen(
    navController: NavController,
    usuarioViewModel: UsuarioViewModel
) {
    // ✅ Estado local SOLO de este formulario (para que NO quede precargado al volver)
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var aceptaTerminos by remember { mutableStateOf(false) }

    // ViewModel para conectarse con la API
    val registerViewModel: RegisterViewModel = viewModel()
    val isLoading by registerViewModel.isLoading.collectAsState()
    val isSuccess by registerViewModel.isSuccess.collectAsState()
    val error by registerViewModel.error.collectAsState()

    // ✅ si el registro fue exitoso, guardar en DataStore y luego navegar
    LaunchedEffect(isSuccess) {
        if (isSuccess == true) {
            usuarioViewModel.guardarUsuario(
                nombre = nombre.trim(),
                email = correo.trim(),
                password = clave
            )
            delay(1500)
            navController.navigate("resumen")
            registerViewModel.resetState()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFEAF0FF))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // Icono superior con degradado
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color(0xFF3F51B5), Color(0xFF81C784))
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "Usuario",
                    tint = Color.White
                )
            }

            Text(
                text = "Crear cuenta",
                color = Color(0xFF3F51B5),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(32.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre", color = Color(0xFF3F51B5)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = correo,
                        onValueChange = { correo = it },
                        label = { Text("Correo electrónico", color = Color(0xFF3F51B5)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = clave,
                        onValueChange = { clave = it },
                        label = { Text("Contraseña", color = Color(0xFF3F51B5)) },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        label = { Text("Dirección", color = Color(0xFF3F51B5)) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = aceptaTerminos,
                            onCheckedChange = { aceptaTerminos = it }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Acepto los términos y condiciones",
                            color = Color(0xFF3F51B5)
                        )
                    }

                    Button(
                        onClick = {
                            val n = nombre.trim()
                            val c = correo.trim()

                            // validación simple (puedes endurecerla si quieres)
                            val ok = n.isNotBlank() &&
                                    c.contains("@") &&
                                    clave.length >= 6 &&
                                    direccion.isNotBlank() &&
                                    aceptaTerminos

                            if (ok) {
                                registerViewModel.register(
                                    email = c,
                                    password = clave,
                                    nombre = n
                                )
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(55.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent),
                        contentPadding = PaddingValues()
                    ) {
                        Box(
                            modifier = Modifier
                                .background(
                                    brush = Brush.horizontalGradient(
                                        colors = listOf(
                                            Color(0xFF4CAF50),
                                            Color(0xFF81C784)
                                        )
                                    ),
                                    shape = RoundedCornerShape(50)
                                )
                                .fillMaxWidth()
                                .height(55.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            when {
                                isLoading -> CircularProgressIndicator(color = Color.White)
                                isSuccess == true -> Text(
                                    text = "Registro exitoso!",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                                else -> Text(
                                    text = "Registrar",
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    error?.let {
                        Text(
                            text = it,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    val navController = rememberNavController()
    val fakeUsuarioVm: UsuarioViewModel = viewModel()
    RegisterScreen(navController = navController, usuarioViewModel = fakeUsuarioVm)
}
