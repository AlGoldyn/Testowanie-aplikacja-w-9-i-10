package com.example.mylab5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.mylab5.data.local.entity.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY id ASC")
    suspend fun getAll(): List<Person>

    @Insert
    suspend fun insert(person: Person)

    @Query("DELETE FROM Person WHERE id = :id")
    suspend fun deleteById(id: Int)
}