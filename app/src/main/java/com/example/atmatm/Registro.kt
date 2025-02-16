package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.dataBase.User
import com.example.atmatm.databinding.ActivityRegistroBinding
import kotlinx.coroutines.launch

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var dbAccess: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        guardarDatos()
        volver()
    }

    private fun volver() {
        binding.volver.setOnClickListener{
            cambiarACreditCards()
        }
    }

    private fun guardarDatos() {
        binding.continuarRegistro.setOnClickListener {
            val email = binding.correo.text.toString().trim()
            val pin = binding.pin.text.toString().trim()
            val confirmPin = binding.confirmarpin.text.toString().trim()
            val nombre = binding.nombre.text.toString().trim()


            if (email.isEmpty() || pin.isEmpty() || confirmPin.isEmpty() || nombre.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }


            if (!Regex("^\\d{4}$").matches(pin)) {
                Toast.makeText(this, "El PIN debe tener exactamente 4 números", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            if (pin != confirmPin) {
                Toast.makeText(this, "Los PINs no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = User(
                email = email,
                pin = pin.toInt(),
                nombre = nombre,
                numeroTarjeta = (0..9999999).random()
            )
            lifecycleScope.launch {
                dbAccess.userDao().insert(user)
                Toast.makeText(this@Registro, "Registro exitoso, inicia sesión", Toast.LENGTH_SHORT).show()
                cambiarACreditCards()
            }
        }
    }

    private fun cambiarACreditCards() {
        val intentCreditCards = Intent(this, CreditCards::class.java)
        intentCreditCards.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        startActivity(intentCreditCards)
        finish()
    }
}
