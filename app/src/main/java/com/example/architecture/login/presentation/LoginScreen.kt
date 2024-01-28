package com.example.architecture.login.presentation

import androidx.compose.animation.expandVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.architecture.LocalSnackbarHostState
import com.example.architecture.common.utils.UiEvent
import com.example.architecture.utils.Fields
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val snackbarHostState = LocalSnackbarHostState.current
    val controller = LocalSoftwareKeyboardController.current

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest {
            when(it) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(it.message)
                }

            }
        }

    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally)
    {


        TextField(value = viewModel.fieldsState[Fields.USERNAME] ?: "", label = { Text(text = "Username")}, onValueChange = { viewModel.onFieldChange(
            Fields.USERNAME, it) })
        TextField(value = viewModel.fieldsState[Fields.PASSWORD] ?: "", visualTransformation = PasswordVisualTransformation(), label = { Text(text = "Password")}, onValueChange = { viewModel.onFieldChange(
            Fields.PASSWORD, it) })

        Button(onClick = {
            viewModel.login()
            controller?.hide()
        },
        ) {
            
            if (viewModel.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.size(20.dp).padding(end = 5.dp)
                )
            }
                Text(text = "Login", fontSize = TextUnit(15F, TextUnitType.Sp))

        }

        Button(onClick = { navController.navigate("register") }) {
            Text(text = "Signup")

        }

    }
}