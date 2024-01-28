package com.example.architecture.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.architecture.common.utils.UserManager
import kotlinx.coroutines.flow.last

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {

    Column {
        Text(text = viewModel.user?.firstName ?: "")
        Text(text = viewModel.user?.lastName ?: "")
        Button(onClick = { viewModel.logout() }
        ) {
            Text(text = "Logout")
        }
    }
}