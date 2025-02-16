package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.atmatm.Home.Companion.ID_USER_HOME
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityRetiroBinding
import kotlinx.coroutines.launch

class Retiro : AppCompatActivity() {
    private lateinit var binding: ActivityRetiroBinding
    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_RETIRO: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        completarDatos()
        retirar()
        volver()
    }

    private fun volver() {
        binding.volver.setOnClickListener {
            cambiarAHome()
        }
    }

    private fun completarDatos() {
        val idUser = intent.getStringExtra(ID_USER_RETIRO)
        if (idUser != null) {
            lifecycleScope.launch {
                val user = dbAccess.userDao().getUserById(idUser)
                val nuevoTexto = "Saldo: ${user.saldo}"
                binding.monto.text = nuevoTexto
            }
        }
    }


    private fun retirar() {
        binding.siguienteRetiro.setOnClickListener {
            if (binding.editMonto.text.isBlank()) {
                binding.editMonto.error = "Ingrese un monto a retirar o seleccione una opción"
                return@setOnClickListener
            } else {
                restarSaldo(binding.editMonto.text.toString().toInt())
            }
        }

        binding.veinteRetiro.setOnClickListener {
            restarSaldo(20)
        }

        binding.cincuentaRetiro.setOnClickListener {
            restarSaldo(50)
        }

        binding.cienRetiro.setOnClickListener {
            restarSaldo(100)
        }

        binding.doscientosRetiro.setOnClickListener {
            restarSaldo(200)
        }
    }

    private fun restarSaldo(retiro: Int) {
        lifecycleScope.launch {
            val idUser = intent.getStringExtra(ID_USER_RETIRO)
            if (idUser != null) {
                val user = dbAccess.userDao().getUserById(idUser)
                if (user.saldo >= retiro) {
                    user.saldo -= retiro
                    dbAccess.userDao().update(user)

                    cambiarAHome()
                }
            }
        }
    }

    private fun cambiarAHome() {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_HOME, intent.getStringExtra(ID_USER_RETIRO))
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }
}