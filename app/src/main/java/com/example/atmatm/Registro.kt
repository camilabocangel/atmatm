package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityRegistroBinding

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_REGISTRO: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        dbAccess = getDatabase(this)

        setContentView(binding.root)
        super.onCreate(savedInstanceState)
        binding.continuarRegistro.setOnClickListener{
            val intentHome = Intent (this, Home::class.java)
            startActivity(intentHome)
        }
    }
}