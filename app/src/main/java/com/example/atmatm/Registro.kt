package com.example.atmatm

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.atmatm.Login.Companion.ID_USER_LOG_IN
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.dataBase.User
import com.example.atmatm.databinding.ActivityRegistroBinding
import kotlinx.coroutines.launch

class Registro : AppCompatActivity() {
    private lateinit var binding: ActivityRegistroBinding
    private lateinit var dbAccess: AppDataBase

    companion object {
        const val ID_USER_REGISTRO: String = "ID_USER"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegistroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbAccess = getDatabase(this)

        setData()
        guardarDatos()
    }

    private fun setData() {
        lifecycleScope.launch {
            val user = dbAccess.userDao().getLastUser()
            if (user != null) {
                cambiarALogIn(user.id.toString())
            }
        }
    }

    private fun guardarDatos() {
        binding.continuarRegistro.setOnClickListener {
            if (binding.nombreText.text.toString() == "" || binding.contrasenaText.text.toString() == "" || binding.confirmarContrasenaText.text.toString() == "") {
                Toast.makeText(this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show()
            } else if (binding.contrasenaText.text != binding.confirmarContrasenaText.text) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            } else {
                lifecycleScope.launch {
                    val user = User(
                        nombre = binding.nombreText.text.toString(),
                        password = binding.contrasenaText.text.toString().toInt(),
                        lastUser = true
                    )
                    val idUser = dbAccess.userDao().insert(user)
                    val lastUser = dbAccess.userDao().getLastUser()
                    if (lastUser != null) {
                        lastUser.lastUser = false
                        dbAccess.userDao().update(lastUser)
                    }
                    cambiarAHome(idUser.toString())
                }
            }
        }
    }

    private fun cambiarAHome(idUser: String) {
        val intentHome = Intent(this, Home::class.java)
        intentHome.putExtra(ID_USER_REGISTRO, idUser)
        intentHome.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentHome)
    }

    private fun cambiarALogIn(idUser: String) {
        val intentLogin = Intent(this, Login::class.java)
        intentLogin.putExtra(ID_USER_LOG_IN, idUser)
        intentLogin.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intentLogin)
    }
}