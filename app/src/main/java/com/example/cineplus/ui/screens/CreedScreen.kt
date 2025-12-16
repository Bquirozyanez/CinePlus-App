package com.example.cineplus.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.cineplus.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreedScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Creed") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color.White)
                .padding(16.dp)
        ) {
            // Poster + info
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(140.dp)
                        .height(210.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.creed),
                        contentDescription = "Póster Creed",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }

                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(
                        text = "CREED",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.ExtraBold
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        AssistChip(onClick = { }, label = { Text("PG-13") })
                        AssistChip(onClick = { }, label = { Text("1h 56m") })
                        AssistChip(onClick = { }, label = { Text("Drama / Acción") })
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sinopsis",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF3F51B5)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text =
                    "Adonis Johnson nunca conoció a su padre, el campeón mundial Apollo Creed. " +
                            "Decidido a seguir su legado, viaja a Filadelfia para buscar a Rocky Balboa, " +
                            "quien acepta entrenarlo. Juntos enfrentarán el camino más duro: demostrar que " +
                            "tiene corazón de campeón dentro y fuera del ring.",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF444444)
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CreedScreenPreview() {
    CreedScreen(navController = rememberNavController())
}
