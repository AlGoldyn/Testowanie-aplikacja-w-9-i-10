package com.example.mylab5.data.remote

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class FirebaseContactsRepository {

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    private fun contactsRef() =
        db.collection("users")
            .document(auth.currentUser!!.uid)
            .collection("contacts")

    fun saveContact(person: FirebasePerson) {
        Log.d("FIREBASE", "Saving contact id=${person.id}")
        contactsRef()
            .document(person.id.toString())
            .set(person)
            .addOnSuccessListener {
                Log.d("FIREBASE", "SAVE OK")
    }
            .addOnFailureListener {
                Log.e("FIREBASE", "SAVE FAIL", it)
            }}
    fun updateContact(person: FirebasePerson) {
        contactsRef()
            .document(person.id.toString())
            .set(person)
    }

    fun deleteContact(id: Int) {
        contactsRef()
            .document(id.toString())
            .delete()
    }
}
