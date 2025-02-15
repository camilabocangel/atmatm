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
        val savedPin = sharedPreferences.getString("PIN", null)

        binding.botonLogin.setOnClickListener {
            val pin = binding.editpin.text.toString().trim()

            if (savedPin.isNullOrEmpty()) {
                Toast.makeText(this, "No hay un PIN registrado. Cree una cuenta.", Toast.LENGTH_SHORT).show()
            } else if (pin == savedPin) {
                startActivity(Intent(this, Home::class.java))
                finish()
            } else {
                Toast.makeText(this, "PIN incorrecto", Toast.LENGTH_SHORT).show()
            }
        }

        binding.crearCuenta.setOnClickListener {
            startActivity(Intent(this, Registro::class.java))
        }
    }
}
