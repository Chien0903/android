package com.example.playstore

data class App(
    val name: String,
    val genre: String,
    val rating: Float,
    val size: String,
    val iconResId: Int = R.drawable.ic_launcher_foreground
)

