package com.example.mylab5.data.remote

import com.example.mylab5.data.local.entity.Person

fun Person.toFirebase() = FirebasePerson(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    phone = this.phone,
    email = this.email,
    birthDate = this.birthDate,
    address = this.address
)

fun FirebasePerson.toLocal() = Person(
    id = this.id,
    firstName = this.firstName,
    lastName = this.lastName,
    phone = this.phone,
    email = this.email,
    birthDate = this.birthDate,
    address = this.address
)
