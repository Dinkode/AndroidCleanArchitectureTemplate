package com.example.architecture

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.core.app.ActivityCompat
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.example.architecture.common.data.utils.LocalVpnService
import com.example.architecture.login.presentation.LoginViewModel
import com.example.architecture.ui.theme.ArchitectureTheme
import dagger.hilt.android.AndroidEntryPoint

val LocalSnackbarHostState = compositionLocalOf {
    SnackbarHostState()
}

@AndroidEntryPoint
class MainActivity : ComponentActivity(), HandleOnPause by HandleOnPauseImpl() {

    private val viewModel:MainViewModel by viewModels()

    fun startVpnService () {
        Log.d("VPN", "Start")
        Intent(this, LocalVpnService::class.java).also {
            it.action = "START_VPN_SERVICE"
            this.startService(it)
        }
    }
    @OptIn(ExperimentalMaterial3Api::class)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {

        register(this)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1)
        }
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

                                    AuthenticationScreens(navController, viewModel)
                                }
                            }
                            navigation(startDestination = "homeRoot", route = "homeScreens") {
                                composable("homeRoot") {
                                    MainScreens(navController)
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