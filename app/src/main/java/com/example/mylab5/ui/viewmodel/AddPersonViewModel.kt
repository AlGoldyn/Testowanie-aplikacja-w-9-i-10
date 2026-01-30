package com.example.mylab5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.remote.FirebaseContactsRepository
import com.example.mylab5.data.remote.toFirebase
import kotlinx.coroutines.launch

class AddPersonViewModel(
    private val db: PersonDatabase,
    private val firebaseRepo: FirebaseContactsRepository
) : ViewModel() {

    fun addPerson(person: Person, onDone: () -> Unit) {
        viewModelScope.launch {

            val newId = db.personDao().insert(person).toInt()
            val withId = person.copy(id = newId)

            firebaseRepo.saveContact(withId.toFirebase())

            onDone()
        }
    }
}
