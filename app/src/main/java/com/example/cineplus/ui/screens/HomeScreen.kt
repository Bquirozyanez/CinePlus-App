package com.example.cineplus.ui.screens

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cineplus.R

//Declaro variables de las peliculas y las vinculo con las imagenes y le meto el scaffold
@Preview(name = "Compact", widthDp = 360, heightDp = 800)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val movies = listOf(
        Movie("Creed", "1h 56m", R.drawable.creed),
        Movie("Kimetsu no Yaiba", "2h 0m", R.drawable.kimetsu),
        Movie("Shrek", "1h 30m", R.drawable.shrek),
        Movie("4 Fantásticos", "2h 5m", R.drawable.fantastic4)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Cartelera",
                        modifier = Modifier.fillMaxWidth().wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(movies) { movie ->
                Cartelera(movie)
            }
        }
    }
}

data class Movie(
    val title: String,
    val duration: String,
    val posterRes: Int //recordatorio de q aca capaz le meto un @drawable pero x el momento no es necesario
)


//Aplico la plantilla de un Card para la forma visual de la cartelera
@Composable
fun Cartelera(movie: Movie) {
    Card(
        shape = RoundedCornerShape(20.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = movie.title,
                    style = MaterialTheme.typography.titleLarge,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = "Duración: ${movie.duration}",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

