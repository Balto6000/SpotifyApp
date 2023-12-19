package com.example.spotifyapp.shared

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.media3.exoplayer.ExoPlayer
import com.example.spotifyapp.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class SpotifyViewModel : ViewModel() {

    private val _exoPlayer: MutableStateFlow<ExoPlayer?> = MutableStateFlow(null)
    val exoPlayer = _exoPlayer.asStateFlow()

    private val _actual = MutableStateFlow(R.raw.audio1)
    val actual = _actual.asStateFlow()

    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    fun crearExoPlayer(context: Context) {
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun hacerSonarMusica(context: Context) {
        val cancionActual = listaCanciones().find { it.audioUrl == _actual.value }
        if (cancionActual != null) {
            var cancion =
                MediaItem.fromUri("android.resource://${context.packageName}/${cancionActual.audioUrl}")
            _exoPlayer.value!!.setMediaItem(cancion)
            _exoPlayer.value!!.playWhenReady = true
            _exoPlayer.value!!.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(playbackState: Int) {
                    if (playbackState == Player.STATE_READY) {
                        _duracion.value = _exoPlayer.value!!.duration.toInt()

                        viewModelScope.launch {
                            while (isActive) {
                                _progreso.value = _exoPlayer.value!!.currentPosition.toInt()
                                delay(1000)
                            }
                        }
                    } else if (playbackState == Player.STATE_BUFFERING) {

                    } else if (playbackState == Player.STATE_ENDED) {
                        siguienteCancion(context)

                    } else if (playbackState == Player.STATE_IDLE) {
                    }
                }
            }
            )
        }
    }

    fun siguienteCancion(context: Context) {
        val listaCanciones = listaCanciones()
        val indexActual = listaCanciones.indexOfFirst { it.audioUrl == _actual.value }

        val newIndex = (indexActual + 1) % listaCanciones.size
        val nuevaCancion = listaCanciones[newIndex]

        _actual.value = nuevaCancion.audioUrl
        cargarYReproducirCancion(context, nuevaCancion)
    }

    fun anteriorCancion(context: Context) {
        val listaCanciones = listaCanciones()
        val indexActual = listaCanciones.indexOfFirst { it.audioUrl == _actual.value }

        val newIndex = if (indexActual == 0) listaCanciones.size - 1 else indexActual - 1
        val nuevaCancion = listaCanciones[newIndex]

        _actual.value = nuevaCancion.audioUrl
        cargarYReproducirCancion(context, nuevaCancion)
    }

    fun reproducirCancion() {
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }

    private fun cargarYReproducirCancion(context: Context, cancion: Cancion) {
        val mediaItem = MediaItem.fromUri("android.resource://${context.packageName}/${cancion.audioUrl}")

        _exoPlayer.value?.setMediaItem(mediaItem)
        _exoPlayer.value?.prepare()
        _exoPlayer.value?.play()
    }

}


