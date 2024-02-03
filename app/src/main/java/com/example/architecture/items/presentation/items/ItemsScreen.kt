package com.example.architecture.items.presentation.items

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController

@Composable
fun ItemsScreen(
    viewModel: ItemsViewModel = hiltViewModel(),
    navController: NavHostController
    ) {
    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

        viewModel.items.value.data?.let {
            LazyColumn(

                content = {
                    items(it)
                    { item ->

                        Button(onClick = {
                            navController.navigate("items/${item.id}")
                        }) {
                            Text(
                                text = item.itemName,
                                style = TextStyle(
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = Color.Green
                                )
                            )
                        }

                    }
                })
        }
    }
}