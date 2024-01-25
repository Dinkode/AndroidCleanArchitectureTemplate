package com.example.architecture.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    Column {


        Button(onClick = { viewModel.logout() }
        ) {
            Text(text = "Logout")
        }
    }
}