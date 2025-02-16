package com.example.atmatm

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

        dbAccess = getDatabase(this)

        iniciarSesion()
        volver()
    }

    private fun volver() {
        binding.volver.setOnClickListener{
            cambiarACreeditCards()
        }
    }

    private fun iniciarSesion() {
        binding.botonLogin.setOnClickListener {
            val idUser = intent.getStringExtra(ID_USER_LOG_IN)
            if (idUser != null) {
                lifecycleScope.launch {
                    val user = dbAccess.userDao().getUserById(idUser)
                    val insertedPin = binding.editpin.text.toString().trim()

                    if (insertedPin.isEmpty()) {
                        Toast.makeText(
                            this@Login,
                            "No hay un PIN válido",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    } else if (!Regex("^\\d{4}$").matches(insertedPin)) {
                        Toast.makeText(
                            this@Login,
                            "El PIN debe tener exactamente 4 números",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (user.pin != insertedPin.toInt()) {
                        Toast.makeText(
                            this@Login,
                            "PIN incorrecto",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        cambiarAHome()
                    }
                }
            }
        }
    }

    private fun cambiarAHome() {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_HOME, intent.getStringExtra(ID_USER_LOG_IN))
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }

    private fun cambiarACreeditCards() {
        val intentCreditCards = Intent(this, CreditCards::class.java)
        intentCreditCards.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentCreditCards)
    }
}