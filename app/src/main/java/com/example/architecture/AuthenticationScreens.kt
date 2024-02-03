package com.example.architecture

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.login.presentation.LoginScreen
import com.example.architecture.login.presentation.LoginViewModel
import com.example.architecture.login.presentation.SignupScreen
import com.example.architecture.ui.theme.ArchitectureTheme
import kotlinx.coroutines.flow.collectLatest
import java.lang.Exception

@Composable
fun AuthenticationScreens(rootNavController: NavHostController, viewModel: LoginViewModel) {

    val authenticationNavController = rememberNavController()

    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        TokenManager(context).getToken().collectLatest {
            if (it != null) {
                try {
                    rootNavController.navigate("homeScreens") {
                        popUpTo("loginScreens") {
                            inclusive = true
                        }
                    }
                } catch (e: Exception) {
                    e.stackTrace
                }
            }
        }
    }

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