package com.example.mylab5.ui.theme

import android.app.DatePickerDialog
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mylab5.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPersonScreen(
    db: PersonDatabase,
    onBack: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var first by remember { mutableStateOf("") }
    var last by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf<LocalDate?>(null) }
    var phone by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }

    var firstError by remember { mutableStateOf<String?>(null) }
    var lastError by remember { mutableStateOf<String?>(null) }
    var birthError by remember { mutableStateOf<String?>(null) }
    var phoneError by remember { mutableStateOf<String?>(null) }
    var emailError by remember { mutableStateOf<String?>(null) }

    val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    fun validate(): Boolean {
        var valid = true

        firstError = if (first.isBlank()) {
            valid = false
            context.getString(R.string.error_required)
        } else null

        lastError = if (last.isBlank()) {
            valid = false
            context.getString(R.string.error_required)
        } else null

        birthError = if (birthDate == null) {
            valid = false
            context.getString(R.string.error_birth_required)
        } else null

        phoneError = if (!phone.matches(Regex("^\\d{9}$"))) {
            valid = false
            context.getString(R.string.error_phone)
        } else null

        emailError = if (
            !email.matches(
                Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
            )
        ) {
            valid = false
            context.getString(R.string.error_email)
        } else null

        return valid
    }

    Column {

        TopAppBar(
            title = { Text(stringResource(R.string.add_title)) },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp)
        ) {

            OutlinedTextField(
                value = first,
                onValueChange = { first = it },
                label = { Text(stringResource(R.string.add_first_name)) },
                isError = firstError != null,
                supportingText = {
                    firstError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = last,
                onValueChange = { last = it },
                label = { Text(stringResource(R.string.add_last_name)) },
                isError = lastError != null,
                supportingText = {
                    lastError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = birthDate?.format(formatter) ?: "",
                onValueChange = {},
                readOnly = true,
                label = { Text(stringResource(R.string.add_birth)) },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            val dialog = DatePickerDialog(
                                context,
                                { _, year, month, day ->
                                    birthDate = LocalDate.of(year, month + 1, day)
                                },
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH)
                            )
                            dialog.datePicker.maxDate = System.currentTimeMillis()
                            dialog.show()
                        }
                    ) {
                        Icon(
                            Icons.Default.CalendarToday,
                            contentDescription = stringResource(R.string.add_birth)
                        )
                    }
                },
                isError = birthError != null,
                supportingText = {
                    birthError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text(stringResource(R.string.add_phone)) },
                isError = phoneError != null,
                supportingText = {
                    phoneError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(R.string.add_email)) },
                isError = emailError != null,
                supportingText = {
                    emailError?.let { Text(it, color = MaterialTheme.colorScheme.error) }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = address,
                onValueChange = { address = it },
                label = { Text(stringResource(R.string.add_address)) },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = {
                    if (validate()) {
                        scope.launch {
                            db.personDao().insert(
                                Person(
                                    firstName = first.trim(),
                                    lastName = last.trim(),
                                    birthDate = birthDate!!.format(formatter),
                                    phone = phone.trim(),
                                    email = email.trim(),
                                    address = address.trim()
                                )
                            )
                            onBack()
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(stringResource(R.string.add_save))
            }
        }
    }
}
