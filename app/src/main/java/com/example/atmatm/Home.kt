package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.atmatm.Deposito.Companion.ID_USER_DEPOSITO
import com.example.atmatm.Retiro.Companion.ID_USER_RETIRO
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_HOME: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        binding.depositoCard.setOnClickListener {
            val intentDeposito = Intent (this, Deposito::class.java)
            intentDeposito.putExtra(ID_USER_DEPOSITO, intent.getStringExtra(ID_USER_HOME))
            startActivity(intentDeposito)
        }

        binding.retiroCard.setOnClickListener {
            val intentRetiro = Intent (this, Retiro::class.java)
            intentRetiro.putExtra(ID_USER_RETIRO, intent.getStringExtra(ID_USER_HOME))
            startActivity(intentRetiro)
        }

    }
}