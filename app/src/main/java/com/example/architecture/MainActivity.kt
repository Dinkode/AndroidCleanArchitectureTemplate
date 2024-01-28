package com.example.architecture

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.login.presentation.LoginViewModel
import com.example.architecture.ui.theme.ArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint
import java.lang.Exception

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity(), HandleOnPause by HandleOnPauseImpl() {

    private val viewModel:MainViewModel by viewModels()

    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        register(this)
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            val snackbarHostState = LocalSnackbarHostState.current
            ArchitectureTheme {
                val navController = rememberNavController()


                LaunchedEffect(key1 = true) {
                    TokenManager(this@MainActivity).getToken().collect() {
                        if (!it.isNullOrBlank()) {
                            try {
                                navController.navigate("homeScreens") {
                                    popUpTo("loginScreens") {
                                        inclusive = true
                                    }
                                }
                            } catch (e: Exception) {
                                e.stackTrace
                            }
                        } else {
                            try {
                                navController.navigate("loginScreens") {
                                    popUpTo("homeScreens") {
                                        inclusive = true
                                    }
                                }
                            } catch (e: Exception) {
                                e.stackTrace
                            }
                        }
                    }
                }
                CompositionLocalProvider (LocalSnackbarHostState provides snackbarHostState) {

                    Scaffold(
                        snackbarHost = {
                            SnackbarHost(hostState = LocalSnackbarHostState.current)
                        }
                    ) {
                        NavHost(navController = navController, startDestination = "loginScreens") {
                            navigation(startDestination = "loginRoot", route = "loginScreens") {

                                composable("loginRoot") {
                                    val viewModel = it.sharedViewModel<LoginViewModel>(navHostController = navController)
                                    AuthenticationScreens(
                                        viewModel
                                    )
                                }
                            }
                            navigation(startDestination = "homeRoot", route = "homeScreens") {
                                composable("homeRoot") {
                                    MainScreens()
                                }
                            }

                        }
                    }
                }
            }
        }
    }
}

@Composable
inline fun <reified T: ViewModel> NavBackStackEntry.sharedViewModel(navHostController: NavHostController): T {
    val navGraphRoute = destination.parent?.route ?: return hiltViewModel()
    val parentEntry = remember(this) {
        navHostController.getBackStackEntry(navGraphRoute)
    }
    return hiltViewModel(parentEntry)
}


interface HandleOnPause {
    fun register(activity: MainActivity)
}

class HandleOnPauseImpl: HandleOnPause {
    override fun register(activity: MainActivity) {
        print("pause")
    }

}