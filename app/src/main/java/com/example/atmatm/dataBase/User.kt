package com.example.atmatm.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var nombre: String,
    var password: Int? = null,
    var saldo: Double = 0.0,
    var lastUser: Boolean
)
