package com.example.mylab5.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Person(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val phone: String = "",
    val email: String = "",
    val address: String = ""
)
