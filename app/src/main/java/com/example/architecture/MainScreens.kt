package com.example.architecture

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.architecture.common.utils.TokenManager
import com.example.architecture.items.presentation.items.ItemsScreen
import com.example.architecture.items.presentation.item_details.ItemDetailsScreen
import com.example.architecture.settings.SettingsScreen
import kotlinx.coroutines.flow.collectLatest
import java.lang.Exception

data class NavItem(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreens(rootNavController: NavHostController) {

    val context = LocalContext.current
    val mainNavController: NavHostController = rememberNavController()
    val items = listOf<NavItem>(
        NavItem("items", Icons.Filled.Home, Icons.Outlined.Home),
        NavItem("settings", Icons.Filled.Settings, Icons.Outlined.Settings)
    )
    val navIndex = rememberSaveable() {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        TokenManager(context).getToken().collectLatest {
            if (it == null) {
                try {
                    rootNavController.navigate("loginScreens") {
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
    Scaffold(bottomBar = {
        NavigationBar {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    selected = index == navIndex.intValue,
                    onClick = {
                        navIndex.intValue = index
                        mainNavController.navigate(item.title)
                              },
                    icon = {
                        Icon(imageVector = if (index == navIndex.intValue) item.selectedIcon
                        else item.unselectedIcon, contentDescription = item.title)
                       })
            }
        }
    }) {
        NavHost(navController = mainNavController, startDestination = "items") {

                composable("items") {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ItemsScreen(navController = mainNavController)
                    }
                }
                composable("items/{id}", arguments = listOf(navArgument("id") { type = NavType.StringType })
                ) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        ItemDetailsScreen(backStackEntry = it)
                    }
                }
                composable("settings") {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        SettingsScreen()
                    }
                }

        }
    }
}