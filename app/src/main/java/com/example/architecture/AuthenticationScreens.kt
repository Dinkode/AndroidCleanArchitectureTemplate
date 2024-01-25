package com.example.architecture

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.architecture.login.presentation.LoginScreen
import com.example.architecture.login.presentation.LoginViewModel
import com.example.architecture.login.presentation.SignupScreen

@Composable
fun AuthenticationScreens(viewModel: LoginViewModel) {

    val authenticationNavController: NavHostController = rememberNavController()

    NavHost(navController = authenticationNavController, startDestination = "login") {

            composable("login") {
                LoginScreen(
                    navController = authenticationNavController,
                    viewModel = viewModel,
                )
            }
            composable("register") {
                SignupScreen(viewModel = viewModel)
            }

    }
}