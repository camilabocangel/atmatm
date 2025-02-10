package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmatm.databinding.ActivityHomeBinding

class Home : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        binding.depositoCard.setOnClickListener {
            val intentDeposito = Intent (this, Deposito::class.java)
            startActivity(intentDeposito)
        }

        binding.retiroCard.setOnClickListener {
            val intentRetiro = Intent (this, Retiro::class.java)
            startActivity(intentRetiro)
        }

    }
}