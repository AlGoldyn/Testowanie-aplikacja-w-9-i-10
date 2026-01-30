package com.example.mylab5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.remote.FirebaseContactsRepository
import com.example.mylab5.data.remote.toFirebase
import com.example.mylab5.data.remote.toLocal
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class ListPersonViewModel(
    private val db: PersonDatabase,
    private val firebaseRepo: FirebaseContactsRepository
) : ViewModel() {

    private val firestore = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private val _persons = MutableStateFlow<List<Person>>(emptyList())
    val persons: StateFlow<List<Person>> = _persons.asStateFlow()

    // ================= LOAD ONLY LOCAL =================

    fun loadLocal() {
        viewModelScope.launch(Dispatchers.IO) {
            _persons.value = db.personDao().getAll()
        }
    }

    // ================= FIREBASE SYNC =================

    fun syncAndLoad() {
        viewModelScope.launch {

            val uid = auth.currentUser?.uid ?: run {
                loadLocal()
                return@launch
            }

            try {
                val snap = firestore.collection("users")
                    .document(uid)
                    .collection("contacts")
                    .get()
                    .await()

                withContext(Dispatchers.IO) {

                    // 🔥 czyścimy lokalną bazę — konto = własne dane
                    db.personDao().deleteAll()

                    snap.documents.forEach { doc ->
                        val remote = doc.toObject(
                            com.example.mylab5.data.remote.FirebasePerson::class.java
                        )

                        if (remote != null) {
                            db.personDao().insert(remote.toLocal().copy(id = 0))
                        }
                    }

                    _persons.value = db.personDao().getAll()
                }

            } catch (e: Exception) {
                // fallback — tylko lokalne
                loadLocal()
            }
        }
    }

    // ================= UPDATE =================

    fun updatePerson(person: Person) {
        viewModelScope.launch(Dispatchers.IO) {

            // ROOM
            db.personDao().update(person)

            // FIREBASE
            firebaseRepo.updateContact(person.toFirebase())

            _persons.value = db.personDao().getAll()
        }
    }
}
