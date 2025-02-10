package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.atmatm.databinding.ActivityRetiroBinding

class Retiro : AppCompatActivity() {
    private lateinit var binding: ActivityRetiroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetiroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.siguienteRetiro.setOnClickListener {
            val intentHome = Intent (this, Home::class.java)
            startActivity(intentHome)
        }
    }
}