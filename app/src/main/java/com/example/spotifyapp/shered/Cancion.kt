package com.example.spotifyapp.shared

import com.example.spotifyapp.R

data class Cancion(
    val nombre: String,
    val imagenId: Int,
    val duracion: String,
    val audioUrl: Int
)

val listaCanciones = listOf(
    Cancion("DIAMOND", R.drawable.cancion1, "3:30", R.raw.audio1),
    Cancion("GOLDEN", R.drawable.cancion2, "4:15", R.raw.audio2),
    Cancion("SILVER", R.drawable.cancion3, "3:45", R.raw.audio3),
    Cancion("PLATINUM", R.drawable.cancion4, "5:00", R.raw.audio4),
    Cancion("RUBY", R.drawable.cancion5, "4:30", R.raw.audio5)
)