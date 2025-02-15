package com.example.atmatm

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.atmatm.dataBase.AppDataBase
import com.example.atmatm.dataBase.AppDataBase.Companion.getDatabase
import com.example.atmatm.databinding.ActivityCreditCardsBinding
import com.example.atmatm.recyclers.RecyclerUsers
import kotlinx.coroutines.launch

class CreditCards : AppCompatActivity() {
    private val recyclerUsers: RecyclerUsers by lazy { RecyclerUsers() }
    private lateinit var binding: ActivityCreditCardsBinding
    private lateinit var dbAccess: AppDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreditCardsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        dbAccess = getDatabase(this)

        setUpRecyclerView()
    }

    private fun setUpRecyclerView() {
        lifecycleScope.launch {
            val listOfUsers = dbAccess.userDao().getEveryUser().toMutableList()
            recyclerUsers.addDataToList(listOfUsers)

            binding.recyclerUsers.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                adapter = recyclerUsers
            }
        }
    }
}