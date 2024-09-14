package com.example.loginmenufirebase

// Alimento.kt
data class Alimento(
    val nombre: String,
    val precio: Double,
    var seleccionado: Boolean = false
)