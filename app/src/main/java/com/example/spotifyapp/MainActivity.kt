package com.example.spotifyapp

import android.graphics.drawable.Icon
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
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
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Shuffle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
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
    //var tiempo by remember { mutableFloatStateOf(0f)}
    Column (horizontalAlignment = CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)) {
        Text(text = "Now Playing")
        Spacer(Modifier.height(10.dp))
        Text(text = "DIAMOND")
        Spacer(Modifier.height(30.dp))
        Image(painter = painterResource(id = R.drawable.cancion1),
            contentDescription = "Imagen Cancion 1",
            modifier = Modifier.clip(shape = RoundedCornerShape(20.dp)))
        Spacer(Modifier.height(10.dp))
        Slider(value = 0f, onValueChange = {  })
        Text(text = 0f.toString(), modifier = Modifier.align(Start))
        Spacer(Modifier.height(60.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Shuffle, contentDescription = "Aleatorio")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.SkipPrevious, contentDescription = "Anterior")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.PlayArrow, contentDescription = "Pusar/Reproducir")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.SkipNext, contentDescription = "Siguiente")
            }
            IconButton(onClick = { /*TODO*/ }) {
                Icon(Icons.Default.Repeat, contentDescription = "Repetir")
            }
        }
    }
}