package com.example.mylab5.ui.screens.delete

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mylab5.R
import com.example.mylab5.data.local.database.PersonDatabase
import com.example.mylab5.data.remote.FirebaseContactsRepository
import com.example.mylab5.ui.viewmodel.DeletePersonVMFactory
import com.example.mylab5.ui.viewmodel.DeletePersonViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeletePersonScreen(
    db: PersonDatabase,
    onBack: () -> Unit
) {

    val vm: DeletePersonViewModel = viewModel(
        factory = DeletePersonVMFactory(
            db,
            FirebaseContactsRepository()
        )
    )

    val persons by vm.persons.collectAsState()

    var toDelete by remember { mutableStateOf<com.example.mylab5.data.local.entity.Person?>(null) }

    LaunchedEffect(Unit) {
        vm.loadPersons()
    }
    Box(modifier = Modifier.fillMaxSize()) {

        Image(
            painter = painterResource(R.drawable.delete),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    Column {

        TopAppBar(
            title = { Text(stringResource(R.string.home_delete)) },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.Default.ArrowBack, null)
                }
            }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {

            items(persons, key = { it.id }) { person ->

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                        .clickable { toDelete = person }
                ) {
                    Column(Modifier.padding(16.dp)) {
                        Text("${person.firstName} ${person.lastName}")
                        Text(person.phone)
                    }
                }
            }
        }
    }

    // ================= CONFIRM DIALOG =================

    if (toDelete != null) {
        AlertDialog(
            onDismissRequest = { toDelete = null },
            title = { Text(stringResource(R.string.delete_confirm_title)) },
            text = {
                Text(
                    stringResource(
                        R.string.delete_confirm_text,
                        "${toDelete!!.firstName} ${toDelete!!.lastName}"
                    )
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        vm.deletePerson(toDelete!!)
                        toDelete = null
                    }
                ) {
                    Text(stringResource(R.string.delete))
                }
            },
            dismissButton = {
                TextButton(onClick = { toDelete = null }) {
                    Text(stringResource(R.string.cancel))
                }
            }
        )
    }
}}
