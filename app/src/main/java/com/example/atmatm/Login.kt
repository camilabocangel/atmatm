package com.example.atmatm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmatm.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

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
        }

        binding.crearCuenta.setOnClickListener {
            val intentRegistro = Intent(this, Registro::class.java)
            startActivity(intentRegistro)
        }
    }
}
