package com.example.spotifyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
        modifier = Modifier.fillMaxSize()
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
        Row {
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {

            }
            Button(onClick = { /*TODO*/ }) {
                
            }
        }
    }
}