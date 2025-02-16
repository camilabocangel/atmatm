package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.atmatm.Home.Companion.ID_USER_HOME
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityDepositoBinding
import kotlinx.coroutines.launch

class Deposito : AppCompatActivity() {
    private lateinit var binding: ActivityDepositoBinding
    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_DEPOSITO: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDepositoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        depositar()
        volver()
    }

    private fun volver() {
        binding.volver.setOnClickListener {
            cambiarAHome()
        }
    }

    private fun depositar() {
        binding.siguienteDeposito.setOnClickListener {
            if (binding.editMonto.text.isBlank()) {
                binding.editMonto.error = "Ingrese un monto a depositar o seleccione una opción"
                return@setOnClickListener
            } else {
                aumentarSaldo(binding.editMonto.text.toString().toInt())
            }
        }

        binding.veinteDeposito.setOnClickListener {
            aumentarSaldo(20)
        }

        binding.cincuentaDeposito.setOnClickListener {
            aumentarSaldo(50)
        }

        binding.cienDeposito.setOnClickListener {
            aumentarSaldo(100)
        }

        binding.doscientosDeposito.setOnClickListener {
            aumentarSaldo(200)
        }
    }

    private fun aumentarSaldo(aumento: Int) {
        lifecycleScope.launch {
            val idUser = intent.getStringExtra(ID_USER_DEPOSITO)
            if (idUser != null) {
                val user = dbAccess.userDao().getUserById(idUser)
                user.saldo += aumento
                dbAccess.userDao().update(user)

                cambiarAHome()
            }
        }
    }

    private fun cambiarAHome() {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_HOME, intent.getStringExtra(ID_USER_DEPOSITO))
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }
}