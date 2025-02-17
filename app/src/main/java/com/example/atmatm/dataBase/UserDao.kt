package com.example.atmatm.dataBase

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserDao {
    @Query("SELECT * FROM User WHERE id = :id")
    suspend fun getUserById(id: String): User

    @Update
    suspend fun update(user: User)

    @Insert
    suspend fun insert(user: User): Long

    @Query("SELECT * FROM User")
    suspend fun getEveryUser(): List<User>
}