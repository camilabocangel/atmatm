package com.example.atmatm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.atmatm.Home.Companion.ID_USER_HOME
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityLoginBinding
import kotlinx.coroutines.launch

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_LOG_IN: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)
        val savedPin = sharedPreferences.getString("PIN", null)

        binding.botonLogin.setOnClickListener {
            val pin = binding.editpin.text.toString().trim()

            if (savedPin.isNullOrEmpty()) {
                Toast.makeText(
                    this,
                    "No hay un PIN registrado. Cree una cuenta.",
                    Toast.LENGTH_SHORT
                ).show()
            } else if (pin == savedPin) {
                startActivity(Intent(this, Home::class.java))
                finish()
            } else {
                Toast.makeText(this, "PIN incorrecto", Toast.LENGTH_SHORT).show()
            }
            dbAccess = getDatabase(this)
        }

        iniciarSesion()

        cambiarARegistro()
    }

    private fun iniciarSesion() {
        val idUser = intent.getStringExtra(ID_USER_LOG_IN)
        if (idUser != null) {
            lifecycleScope.launch {
                val user = dbAccess.userDao().getUserById(idUser)
                if (user.password != binding.contrasenaLogin.toString().toInt()) {
                    Toast.makeText(this@Login, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                } else {
                    cambiarAHome()
                }
            }
        }
    }

    private fun cambiarARegistro() {
        binding.crearCuenta.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }

    private fun cambiarAHome() {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_HOME, intent.getStringExtra(ID_USER_LOG_IN))
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }
}