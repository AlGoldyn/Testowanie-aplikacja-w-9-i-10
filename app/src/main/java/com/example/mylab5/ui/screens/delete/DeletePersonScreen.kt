package com.example.mylab5.ui.screens.delete

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mylab5.R
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.remote.FirebaseContactsRepository
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePersonScreen(
    db: PersonDatabase,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val firebaseRepo = remember { FirebaseContactsRepository() }

    var persons by remember { mutableStateOf<List<Person>>(emptyList()) }
    var search by remember { mutableStateOf("") }
    var personToDelete by remember { mutableStateOf<Person?>(null) }

    LaunchedEffect(Unit) {
        persons = db.personDao()
            .getAll()
            .sortedBy { it.firstName.trim().lowercase() }
    }

    val filteredPersons = persons.filter {
        search.isBlank() ||
                it.firstName.contains(search, true) ||
                it.lastName.contains(search, true) ||
                it.phone.contains(search, true) ||
                it.email.contains(search, true)
    }

    Column {

        TopAppBar(
            title = { Text(stringResource(R.string.delete_title)) },
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
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text(stringResource(R.string.search)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn {
                items(
                    items = filteredPersons,
                    key = { p: Person -> p.id }
                ) { person ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
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

                            IconButton(
                                onClick = { personToDelete = person }
                            ) {
                                Icon(
                                    Icons.Default.Delete,
                                    contentDescription = stringResource(R.string.delete),
                                    tint = Color.Red
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // ================= CONFIRM =================

    if (personToDelete != null) {
        AlertDialog(
            onDismissRequest = { personToDelete = null },
            title = { Text(stringResource(R.string.confirmation)) },
            text = {
                Text(
                    stringResource(
                        R.string.confirm_delete_message,
                        "${personToDelete!!.firstName} ${personToDelete!!.lastName}"
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        scope.launch {

                            val idInt = personToDelete!!.id

                            db.personDao().deleteById(idInt)

                            persons = db.personDao()
                                .getAll()
                                .sortedBy { it.firstName.trim().lowercase() }

                            firebaseRepo.deleteContact(idInt)

                            personToDelete = null
                        }
                    }
                ) {
                    Text(
                        text = stringResource(R.string.delete),
                        color = Color.Red
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { personToDelete = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}
