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

        binding.botonLogin.setOnClickListener {
            val email = binding.editcorreo.text.toString().trim()
            val pin = binding.editpin.text.toString().trim()

            val savedEmail = sharedPreferences.getString("EMAIL", null)
            val savedPin = sharedPreferences.getString("PIN", null)

            if (email.isEmpty() || pin.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (email == savedEmail && pin == savedPin) {
                val intentHome = Intent(this, Home::class.java)
                startActivity(intentHome)
                finish()
            } else {
                Toast.makeText(this, "Correo o PIN incorrectos", Toast.LENGTH_SHORT).show()
            }
        dbAccess = getDatabase(this)

        iniciarSesion()

        cambiarARegistro()
    }

    private fun iniciarSesion() {
        val idUser = intent.getStringExtra(ID_USER_LOG_IN)
        if (idUser != null) {
            lifecycleScope.launch {
                val user = dbAccess.userDao().getUserById(idUser)
                if(user.password != binding.contrasenaLogin.toString().toInt()) {
                    Toast.makeText(this@Login, "Contraseña incorrecta", Toast.LENGTH_SHORT).show()
                } else {
                    cambiarAHome()
                }
            }
        }
    }

    private fun cambiarARegistro() {
        binding.crearCuenta.setOnClickListener {
            val intentRegistro = Intent(this, Registro::class.java)
            startActivity(intentRegistro)
        }
    }

    private fun cambiarAHome() {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_HOME, intent.getStringExtra(ID_USER_LOG_IN))
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }
}