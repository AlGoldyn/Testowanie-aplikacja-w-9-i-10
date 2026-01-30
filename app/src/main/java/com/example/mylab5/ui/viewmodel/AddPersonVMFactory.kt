package com.example.mylab5.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.remote.FirebaseContactsRepository

class AddPersonVMFactory(
    private val db: PersonDatabase,
    private val repo: FirebaseContactsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return AddPersonViewModel(db, repo) as T
    }
}
