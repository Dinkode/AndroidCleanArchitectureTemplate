package com.example.architecture.login.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.architecture.utils.Fields

@Composable
fun SignupScreen(
    viewModel: LoginViewModel = hiltViewModel(),
) {

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(value = viewModel.fieldsState[Fields.FIRST_NAME] ?: "", label = { Text(text = "First Name")}, onValueChange = { viewModel.onFieldChange(Fields.FIRST_NAME, it) })
            TextField(value = viewModel.fieldsState[Fields.LAST_NAME] ?: "", label = { Text(text = "Last Name")}, onValueChange = { viewModel.onFieldChange(Fields.LAST_NAME, it) })
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = viewModel.fieldsState[Fields.TERMS_AND_CONDITIONS].toBoolean(),
                    onCheckedChange = { viewModel.onFieldChange(Fields.TERMS_AND_CONDITIONS, it.toString()) })
                Spacer(Modifier.size(6.dp))
                Text(text = "Terms and Conditions")
            }
        }
    }

}
