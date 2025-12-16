package com.example.cineplus.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.R
import com.example.cineplus.viewmodel.DarkModeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    darkModeViewModel: DarkModeViewModel = viewModel()
) {
    val movies = listOf(
        Movie("Creed", "1h 56m", R.drawable.creed),
        Movie("Kimetsu no Yaiba", "2h 0m", R.drawable.kimetsu),
        Movie("Shrek", "1h 30m", R.drawable.shrek),
        Movie("4 Fantásticos", "2h 5m", R.drawable.fantastic4)
    )

    val isDarkModeState by darkModeViewModel.isDarkMode.collectAsState()
    val showMessage by darkModeViewModel.showMessage.collectAsState()

    val context = LocalContext.current
    var capturedImage by remember { mutableStateOf<Bitmap?>(null) }

    // launcher cámara
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        capturedImage = bitmap
    }

    // launcher permiso cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            cameraLauncher.launch(null)
        } else {
            Toast.makeText(
                context,
                "Se necesita el permiso de cámara para tomar fotos",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    if (isDarkModeState == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val isDarkMode = isDarkModeState ?: false

    val backgroundColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF202123) else Color(0xFFF5F5F7),
        label = "backgroundColor"
    )

    val cardColor by animateColorAsState(
        targetValue = if (isDarkMode) Color(0xFF2D2F34) else Color.White,
        label = "cardColor"
    )

    val modeLabel by remember(isDarkMode) {
        derivedStateOf {
            if (isDarkMode) "Modo oscuro activado" else "Modo oscuro desactivado"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Cartelera",
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold),
                        color = if (isDarkMode) Color.White else Color(0xFF222222),
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                },
                actions = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                       
                        IconButton(onClick = { navController.navigate("modificar") }) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = "Perfil"
                            )
                        }

                        Text(
                            text = "QR",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                            color = if (isDarkMode) Color.White else Color(0xFF222222)
                        )

                        // Cámara
                        IconButton(
                            onClick = {
                                val hasPermission = ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.CAMERA
                                ) == PackageManager.PERMISSION_GRANTED

                                if (hasPermission) {
                                    cameraLauncher.launch(null)
                                } else {
                                    cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = "Abrir cámara"
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        Text(
                            text = if (isDarkMode) "🌙" else "☀️",
                            style = MaterialTheme.typography.bodyMedium,
                            color = if (isDarkMode) Color.White else Color(0xFF222222)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Switch(
                            checked = isDarkMode,
                            onCheckedChange = { darkModeViewModel.toggleDarkMode() }
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Text(
                text = modeLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = if (isDarkMode) Color(0xFFDDDDDD) else Color(0xFF666666)
            )

            AnimatedVisibility(
                visible = showMessage,
                enter = slideInVertically { fullHeight -> fullHeight / 2 } + fadeIn(),
                exit = slideOutVertically { fullHeight -> fullHeight / 2 } + fadeOut()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.18f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "preferencia guardada",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            capturedImage?.let { bitmap ->
                Image(
                    bitmap = bitmap.asImageBitmap(),
                    contentDescription = "Foto capturada",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 16.dp),
                    contentScale = ContentScale.Crop
                )
            }

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(movies) { movie ->
                    Cartelera(
                        movie = movie,
                        cardColor = cardColor,
                        isDarkMode = isDarkMode,
                        onClick = {
                            when (movie.title) {
                                "Creed" -> navController.navigate("creed")
                                "Kimetsu no Yaiba" -> navController.navigate("kimetsu")
                                "Shrek" -> navController.navigate("shrek")
                                "4 Fantásticos" -> navController.navigate("fantastic4")
                            }
                        }
                    )
                }

                // ✅ Footer “Conócenos”
                item {
                    Spacer(modifier = Modifier.height(32.dp))

                    Divider(
                        thickness = 1.dp,
                        color = Color(0xFF2C2C2C)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Conócenos",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isDarkMode) Color(0xFFBFD0FF) else Color(0xFF2C2C2C),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate("conocenos") }
                            .padding(vertical = 10.dp)
                    )
                }
            }
        }
    }
}

data class Movie(
    val title: String,
    val duration: String,
    val posterRes: Int
)

@Composable
fun Cartelera(
    movie: Movie,
    cardColor: Color,
    isDarkMode: Boolean,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .width(120.dp)
                    .fillMaxHeight()
            ) {
                Image(
                    painter = painterResource(id = movie.posterRes),
                    contentDescription = "Póster de ${movie.title}",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { onClick() }
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    color = if (isDarkMode) Color.White else Color(0xFF1C1B1F),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.clickable { onClick() }
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "Duración: ${movie.duration}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isDarkMode) Color(0xFFCCCCCC) else Color(0xFF444444)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(navController = rememberNavController())
}
