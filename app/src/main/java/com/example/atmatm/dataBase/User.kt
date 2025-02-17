package com.example.atmatm.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nombre: String,
    val email: String,
    var pin: Int? = null,
    var saldo: Double = 0.0,
    val numeroTarjeta: Int
)
