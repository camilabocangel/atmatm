package com.example.atmatm

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.atmatm.databinding.ActivityRegistroBinding

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("ATM_PREFS", Context.MODE_PRIVATE)

        binding.continuarRegistro.setOnClickListener {
            val email = binding.correo.text.toString().trim()
            val pin = binding.pin.text.toString().trim()
            val confirmPin = binding.confirmarpin.text.toString().trim()
            val montoText = binding.ingresarmonto.text.toString().trim()


            if (email.isEmpty() || pin.isEmpty() || confirmPin.isEmpty() || montoText.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            if (!Regex("^\\d{4}$").matches(pin)) {
                Toast.makeText(this, "El PIN debe tener exactamente 4 números", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (pin != confirmPin) {
                Toast.makeText(this, "Los PINs no coinciden", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val monto = montoText.toDoubleOrNull()
            if (monto == null || monto < 0) {
                Toast.makeText(this, "Ingresa un monto válido (mayor o igual a 0)", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            with(sharedPreferences.edit()) {
                putString("EMAIL", email)
                putString("PIN", pin)
                putFloat("MONTO", monto.toFloat())
                apply()
            }

            Toast.makeText(this, "Registro exitoso, inicia sesión", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, Login::class.java))
            finish()
        }
    }
}
