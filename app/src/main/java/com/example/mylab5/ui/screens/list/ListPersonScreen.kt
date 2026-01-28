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
import com.example.mylab5.data.local.entity.Person
import com.example.mylab5.data.local.database.PersonDatabase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListPersonScreen(
    db: PersonDatabase,
    refreshTrigger: MutableState<Boolean>,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()

    var persons by remember { mutableStateOf<List<Person>>(emptyList()) }
    var selectedPerson by remember { mutableStateOf<Person?>(null) }
    var search by remember { mutableStateOf("") }

    LaunchedEffect(refreshTrigger.value) {
            persons = db.personDao().getAll()

    }

    val filteredPersons = persons
        .filter {
            search.isBlank() ||
                    it.firstName.contains(search, true) ||
                    it.lastName.contains(search, true) ||
                    it.phone.contains(search, true) ||
                    it.email.contains(search, true)
        }
        .sortedBy { it.firstName.lowercase() }

    Column {
        // ===== TOP BAR =====
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
                    key = { person -> person.id }
                ) { person ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp)
                            .clickable { selectedPerson = person },
                        elevation = CardDefaults.cardElevation(2.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "${person.firstName} ${person.lastName}",
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = stringResource(
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

    if (selectedPerson != null) {
        AlertDialog(
            onDismissRequest = { selectedPerson = null },
            title = {
                Text(
                    "${selectedPerson!!.firstName} ${selectedPerson!!.lastName}"
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState())
                ) {
                    Text(
                        stringResource(
                            R.string.phone_with_value,
                            selectedPerson!!.phone
                        )
                    )
                    Text(
                        stringResource(
                            R.string.email_with_value,
                            selectedPerson!!.email
                        )
                    )
                    Text(
                        stringResource(
                            R.string.birth_date_with_value,
                            selectedPerson!!.birthDate
                        )
                    )
                    Text(
                        stringResource(
                            R.string.address_with_value,
                            selectedPerson!!.address
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedPerson = null }) {
                    Text(stringResource(R.string.ok))
                }
            }
        )
    }
}
