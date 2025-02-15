package com.example.atmatm.recyclers;

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater;
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.atmatm.Home
import com.example.atmatm.dataBase.User
import com.example.atmatm.databinding.CreditCardBinding

class RecyclerUsers : RecyclerView.Adapter<RecyclerUsers.UserViewHolder>() {
    private val dataList = mutableListOf<User>()
    private var context: Context? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerUsers.UserViewHolder {
        context = parent.context

        return UserViewHolder(CreditCardBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerUsers.UserViewHolder, position: Int) {
        holder.binding(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size

    inner class UserViewHolder(private val binding: CreditCardBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(user: User) {
            val nombre = user.nombre
            val numeroTarjeta = user.numeroTarjeta.toString()
            binding.numeroTarjeta.text = numeroTarjeta
            binding.nombreUsuario.text = nombre

            binding.tarjeta.setOnClickListener{
                val home = Intent(context, Home::class.java)
                home.putExtra(Home.ID_USER_HOME, user.id.toString())
                context?.startActivity(home)
            }
        }
    }

    fun addDataToList(list: MutableList<User>) {
        dataList.clear()
        dataList.addAll(list)
    }
}
