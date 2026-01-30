package com.example.mylab5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.remote.FirebaseContactsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DeletePersonViewModel(
    private val db: PersonDatabase,
    private val firebaseRepo: FirebaseContactsRepository
) : ViewModel() {

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons.asStateFlow()

    fun loadPersons() {
        viewModelScope.launch(Dispatchers.IO) {
            _persons.value = db.personDao().getAll()
        }
    }

    fun deletePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {

            // ROOM
            db.personDao().deleteById(person.id)

            // FIREBASE
            firebaseRepo.deleteContact(person.id)

            // refresh
            _persons.value = db.personDao().getAll()
        }
    }
}
