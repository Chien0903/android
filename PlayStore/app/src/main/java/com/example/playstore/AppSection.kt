package com.example.playstore

data class AppSection(
    val title: String,
    val isSponsored: Boolean = false,
    val apps: List<App>,
    val isHorizontal: Boolean = true
)

