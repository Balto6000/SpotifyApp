package com.example.spotifyapp.shared

import com.example.spotifyapp.R

data class Cancion(
    val nombre: String,
    val artista: String,
    val imagenId: Int,
    val duracion: String,
    val audioUrl: Int
)

val listaCanciones = listOf(
    Cancion("DIAMOND", "Desire The Unknown", R.drawable.cancion1, "2:48", R.raw.audio1),
    Cancion("GOLDEN", "Rilès", R.drawable.cancion2, "3:24", R.raw.audio2),
    Cancion("SILVER", "Ariis", R.drawable.cancion3, "2:13", R.raw.audio3),
    Cancion("PLATINUM", "Sauceboy Lex", R.drawable.cancion4, "3:14", R.raw.audio4),
    Cancion("RUBY", "Jarris, arane", R.drawable.cancion5, "2:34", R.raw.audio5)
)