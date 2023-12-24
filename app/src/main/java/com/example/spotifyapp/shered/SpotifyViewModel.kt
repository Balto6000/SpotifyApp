package com.example.spotifyapp.shared

import android.content.Context
import androidx.compose.runtime.mutableStateOf
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

    private val _duracion = MutableStateFlow(0)
    val duracion = _duracion.asStateFlow()

    private val _progreso = MutableStateFlow(0)
    val progreso = _progreso.asStateFlow()

    private val _reproduciendose = MutableStateFlow(true)
    val reproduciendose = _reproduciendose.asStateFlow()

    var indiceCancionActual = mutableStateOf(0)
    var cancionActual = mutableStateOf(listaCanciones[indiceCancionActual.value])

    private val _modoReproduccion = MutableStateFlow(Reproduccion.NORMAL)
    val modoReproduccion = _modoReproduccion.asStateFlow()

    enum class Reproduccion {
        NORMAL,
        ALEATORIA,
        BUCLE
    }

    fun crearExoPlayer(context: Context) {
        _exoPlayer.value = ExoPlayer.Builder(context).build()
        _exoPlayer.value!!.prepare()
        _exoPlayer.value!!.playWhenReady = true
    }

    fun hacerSonarMusica(context: Context) {
        val cancion =
            MediaItem.fromUri("android.resource://${context.packageName}/${cancionActual.value.audioUrl}")
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

    fun siguienteCancion(context: Context) {
        if (_modoReproduccion.value == Reproduccion.ALEATORIA) {
            val nuevoIndice = (0 until listaCanciones.size).random()
            indiceCancionActual.value = nuevoIndice
        } else if (_modoReproduccion.value == Reproduccion.BUCLE) {
        } else {
            indiceCancionActual.value = (indiceCancionActual.value + 1) % listaCanciones.size
        }

        cancionActual.value = listaCanciones[indiceCancionActual.value]
        cargarYReproducirCancion(context, cancionActual.value)
        _reproduciendose.value = !_exoPlayer.value!!.isPlaying
    }


    fun anteriorCancion(context: Context) {
        if (_modoReproduccion.value == Reproduccion.ALEATORIA) {
            if (_progreso.value <= 3000) {
                val nuevoIndice = (0 until listaCanciones.size).random()
                indiceCancionActual.value = nuevoIndice
            }
        } else if (_modoReproduccion.value == Reproduccion.BUCLE) {
        } else {
            if (_progreso.value <= 3000) {
                indiceCancionActual.value =
                    if (indiceCancionActual.value > 0) {
                        indiceCancionActual.value - 1
                    } else {
                        listaCanciones.size - 1
                    }
            }
        }

        cancionActual.value = listaCanciones[indiceCancionActual.value]
        cargarYReproducirCancion(context, cancionActual.value)
        _reproduciendose.value = !_exoPlayer.value!!.isPlaying
    }


    fun reproducirCancion() {
        _reproduciendose.value = !_exoPlayer.value!!.isPlaying
        if (_exoPlayer.value!!.isPlaying) {
            _exoPlayer.value!!.pause()
        } else {
            _exoPlayer.value!!.play()
        }
    }

    fun reproducirAleatoria() {
        _modoReproduccion.value =
            if (_modoReproduccion.value != Reproduccion.ALEATORIA) Reproduccion.ALEATORIA else Reproduccion.NORMAL
        _exoPlayer.value?.shuffleModeEnabled = (_modoReproduccion.value == Reproduccion.ALEATORIA)
    }

    fun reproducirBucle() {
        _modoReproduccion.value =
            if (_modoReproduccion.value != Reproduccion.BUCLE) Reproduccion.BUCLE else Reproduccion.NORMAL
        _exoPlayer.value?.repeatMode = when (_modoReproduccion.value) {
            Reproduccion.BUCLE -> Player.REPEAT_MODE_ONE
            else -> Player.REPEAT_MODE_OFF
        }
    }

    fun actualizarProgresoCancion(nuevaPosicion: Int) {
        val exoPlayer = _exoPlayer.value ?: return

        exoPlayer.seekTo(nuevaPosicion.toLong())
    }

    private fun cargarYReproducirCancion(context: Context, cancion: Cancion) {
        val mediaItem =
            MediaItem.fromUri("android.resource://${context.packageName}/${cancion.audioUrl}")

        _exoPlayer.value?.setMediaItem(mediaItem)
        _exoPlayer.value?.prepare()
        _exoPlayer.value?.play()
    }

}


