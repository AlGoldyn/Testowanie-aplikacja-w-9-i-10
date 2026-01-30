package com.example.mylab5.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mylab5.R
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.remote.FirebasePerson
import com.example.mylab5.data.remote.toLocal
import com.example.mylab5.data.remote.toFirebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPersonScreen(
    db: PersonDatabase,
    refreshTrigger: MutableState<Boolean>,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()
    val uid = auth.currentUser?.uid

    var persons by remember { mutableStateOf<List<Person>>(emptyList()) }
    var selectedPerson by remember { mutableStateOf<Person?>(null) }
    var editingPerson by remember { mutableStateOf<Person?>(null) }
    var search by remember { mutableStateOf("") }

    // ================= FIREBASE → ROOM SYNC =================

    LaunchedEffect(refreshTrigger.value) {
        uid?.let { userId ->

            val snap = firestore.collection("users")
                .document(userId)
                .collection("contacts")
                .get()
                .await()

            withContext(Dispatchers.IO) {

                val dao = db.personDao()

                snap.documents.forEach { doc ->
                    val remote = doc.toObject(FirebasePerson::class.java)
                    if (remote != null) {
                        dao.insert(remote.toLocal()) // ✅ REPLACE by ID
                    }
                }

                persons = dao.getAll()
            }
        }
    }

    // ================= FILTER =================

    val filteredPersons = persons
        .filter {
            search.isBlank() ||
                    it.firstName.contains(search, true) ||
                    it.lastName.contains(search, true) ||
                    it.phone.contains(search, true) ||
                    it.email.contains(search, true)
        }
        .sortedBy { it.firstName.lowercase() }

    // ================= UI =================

    Column {

        TopAppBar(
            title = { Text(stringResource(R.string.list_title)) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        contentDescription = stringResource(R.string.back)
                    )
                }
            }
        )

        Column(
            Modifier.fillMaxSize().padding(16.dp)
        ) {

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            LazyColumn {
                items(filteredPersons, key = { it.id }) { person ->

                    Card(
                        Modifier.fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedPerson = person },
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(Modifier.padding(16.dp)) {
                            Text(
                                "${person.firstName} ${person.lastName}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                stringResource(
                                    R.string.phone_with_value,
                                    person.phone
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    // ================= DETAILS =================

    if (selectedPerson != null) {
        AlertDialog(
            onDismissRequest = { selectedPerson = null },
            title = {
                Text("${selectedPerson!!.firstName} ${selectedPerson!!.lastName}")
            },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    Text(stringResource(R.string.phone_with_value, selectedPerson!!.phone))
                    Text(stringResource(R.string.email_with_value, selectedPerson!!.email))
                    Text(stringResource(R.string.birth_date_with_value, selectedPerson!!.birthDate))
                    Text(stringResource(R.string.address_with_value, selectedPerson!!.address))
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedPerson = null }) {
                    Text(stringResource(R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        editingPerson = selectedPerson
                        selectedPerson = null
                    }
                ) {
                    Text(stringResource(R.string.edit))
                }
            }
        )
    }

    // ================= EDIT =================

    if (editingPerson != null) {

        var firstName by remember { mutableStateOf(editingPerson!!.firstName) }
        var lastName by remember { mutableStateOf(editingPerson!!.lastName) }
        var phone by remember { mutableStateOf(editingPerson!!.phone) }
        var email by remember { mutableStateOf(editingPerson!!.email) }
        var birth by remember { mutableStateOf(editingPerson!!.birthDate) }
        var address by remember { mutableStateOf(editingPerson!!.address) }

        AlertDialog(
            onDismissRequest = { editingPerson = null },
            title = { Text(stringResource(R.string.edit_contact)) },
            text = {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    OutlinedTextField(firstName, { firstName = it })
                    OutlinedTextField(lastName, { lastName = it })
                    OutlinedTextField(phone, { phone = it })
                    OutlinedTextField(email, { email = it })
                    OutlinedTextField(birth, { birth = it })
                    OutlinedTextField(address, { address = it })
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {

                            val updated = editingPerson!!.copy(
                                firstName = firstName,
                                lastName = lastName,
                                phone = phone,
                                email = email,
                                birthDate = birth,
                                address = address
                            )

                            db.personDao().update(updated)

                            uid?.let {
                                firestore.collection("users")
                                    .document(it)
                                    .collection("contacts")
                                    .document(updated.id.toString())
                                    .set(updated.toFirebase())
                            }

                            persons = db.personDao().getAll()
                            editingPerson = null
                        }
                    }
                ) {
                    Text(stringResource(R.string.save))
                }
            },
            dismissButton = {
                TextButton(onClick = { editingPerson = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
