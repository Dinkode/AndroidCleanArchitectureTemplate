package com.example.architecture.items.presentation.item_details

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ItemDetailsScreen(
    viewModel: ItemDetailsViewModel = hiltViewModel()
) {
    Column {
        Text(text = viewModel.state.data?.itemName ?: "")
        Button(onClick = { /*TODO*/ }) {
            Text(text = viewModel.state.data?.id.toString() ?: "")
        }
    }
}