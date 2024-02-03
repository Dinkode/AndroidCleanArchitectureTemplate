package com.example.architecture.items.presentation.item_details

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import coil.compose.AsyncImage
import com.example.architecture.items.domain.models.ItemDetailsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun ItemDetailsScreen(
    viewModel: ItemDetailsViewModel = hiltViewModel(),
    backStackEntry: NavBackStackEntry
) {
    val itemDetailsState = viewModel.itemDetails.value
    LaunchedEffect(key1 = true) {
        val id = backStackEntry.arguments?.getString("id")
        withContext(Dispatchers.IO) {
            id?.let { viewModel.getItemDetails(it)}
        }

    }
    if (itemDetailsState.isLoading) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
    } else {
    Column {
        Text(text = itemDetailsState.data?.title ?: "")
        Text(text = itemDetailsState.data?.description ?: "")

        itemDetailsState.data?.images?.forEach {
            AsyncImage(
                model = it,
                contentDescription = null,
            )
        }
    }
    }
}