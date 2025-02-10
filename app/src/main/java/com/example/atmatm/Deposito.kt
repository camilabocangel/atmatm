package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmatm.databinding.ActivityDepositoBinding

class Deposito : AppCompatActivity() {
    private lateinit var binding: ActivityDepositoBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDepositoBinding.inflate(layoutInflater)

        binding.siguienteDeposito.setOnClickListener {
            val intentHome = Intent (this, Home::class.java)
            startActivity(intentHome)
        }
    }
}