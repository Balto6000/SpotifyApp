package com.example.spotifyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.spotifyapp.shared.SpotifyViewModel
import com.example.spotifyapp.ui.theme.SpotifyAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SpotifyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SpotifyApp()
                }
            }
        }
    }
}

@Composable
fun SpotifyApp() {
    val contexto = LocalContext.current
    val spotifyViewModel: SpotifyViewModel = viewModel()
    val cancionActual = spotifyViewModel.cancionActual
    val reproduciendose = spotifyViewModel.reproduciendose.collectAsState()
    val progreso = spotifyViewModel.progreso.collectAsState()

    LaunchedEffect(Unit) {
        spotifyViewModel.crearExoPlayer(contexto)
        spotifyViewModel.hacerSonarMusica(contexto)
    }

    Column(
        horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .padding(top = 50.dp)
    ) {
        Text(text = "Now Playing")
        Spacer(Modifier.height(10.dp))
        Text(text = cancionActual.value.nombre + " - " + cancionActual.value.artista)
        Image(
            painter = painterResource(id = cancionActual.value.imagenId),
            contentDescription = "Imagen Cancion ${cancionActual.value.nombre}",
            modifier = Modifier
                .clip(shape = RoundedCornerShape(20.dp))
                .weight(1f)
        )
        Slider(
            value = progreso.value.toFloat(),
            onValueChange = { newValue ->
                spotifyViewModel.actualizarProgresoCancion(newValue.toInt())
            },
            valueRange = 0f..spotifyViewModel.duracion.collectAsState().value.toFloat()
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${
                    (progreso.value / 60000).toString().padStart(1, '0')
                }:${(progreso.value % 60000 / 1000).toString().padStart(2, '0')}"
            )
            Text(text = cancionActual.value.duracion)
        }

        Spacer(Modifier.height(60.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(
                onClick = { spotifyViewModel.reproducirAleatoria() }) {
                Icon(
                    Icons.Default.Shuffle, contentDescription = "Aleatorio",
                    tint = if (spotifyViewModel.modoReproduccion.collectAsState().value == SpotifyViewModel.Reproduccion.ALEATORIA) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
            IconButton(onClick = { spotifyViewModel.anteriorCancion(contexto) }) {
                Icon(
                    Icons.Default.SkipPrevious,
                    contentDescription = "Anterior"
                )
            }
            IconButton(onClick = { spotifyViewModel.reproducirCancion() }) {
                val iconoReproduccion = if (reproduciendose.value) {
                    Icons.Default.Pause
                } else {
                    Icons.Default.PlayArrow
                }
                Icon(iconoReproduccion, contentDescription = "Pausar/Reproducir")
            }
            IconButton(onClick = { spotifyViewModel.siguienteCancion(contexto) }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Siguiente")
            }
            IconButton(onClick = { spotifyViewModel.reproducirBucle() }) {
                Icon(
                    Icons.Default.Repeat, contentDescription = "Repetir",
                    tint = if (spotifyViewModel.modoReproduccion.collectAsState().value == SpotifyViewModel.Reproduccion.BUCLE) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onBackground
                    }
                )
            }
        }
    }
}