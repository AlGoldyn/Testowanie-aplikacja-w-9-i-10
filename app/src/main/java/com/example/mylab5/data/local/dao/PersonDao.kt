package com.example.mylab5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mylab5.data.local.entity.Person

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY id ASC")
    suspend fun getAll(): List<Person>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(person: Person): Long


    @Query("DELETE FROM Person WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Update
    suspend fun update(person: Person)
    @Query("DELETE FROM Person")
    suspend fun clearAll()
    @Query("DELETE FROM Person")
    suspend fun deleteAll()


}
