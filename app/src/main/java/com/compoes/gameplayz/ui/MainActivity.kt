package com.compoes.gameplayz.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.compoes.gameplayz.ui.common.NavigationGraph
import com.compoes.gameplayz.ui.theme.DarkGray
import com.compoes.gameplayz.ui.theme.GamePlayzTheme
import com.compoes.gameplayz.ui.theme.Green
import com.compoes.gameplayz.utils.Event
import com.compoes.gameplayz.utils.EventBus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val lifecycleOwner = LocalLifecycleOwner.current.lifecycle
            LaunchedEffect(key1 = lifecycleOwner) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    EventBus.events.collect { event ->
                        when (event) {
                            is Event.Toast -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    event.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
            }
            GamePlayzTheme {
                MainView()
            }
        }
    }
}

@Composable
private fun MainView() {
    val navController = rememberNavController()
    val bottomBarState = rememberSaveable {
        (mutableStateOf(true))
    }
    Scaffold(bottomBar = {
        BottomNavigation(navController = navController, bottomBarState = bottomBarState)
    }) { padding ->
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        when (navBackStackEntry?.destination?.route) {
            Screen.HomeScreen.route, Screen.FavoriteScreen.route, Screen.AboutScreen.route -> {
                bottomBarState.value = true
            }

            else -> {
                bottomBarState.value = false
            }
        }
        NavigationGraph(
            navController = navController,
            modifier = Modifier.padding(bottom = padding.calculateBottomPadding())
        )
    }
}

@Composable
private fun BottomNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    bottomBarState: MutableState<Boolean>,
) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Favorite,
        BottomNavItem.About,
    )
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
    ) {
        NavigationBar(
            containerColor = if (isSystemInDarkTheme()) DarkGray else Color.White,
            modifier = modifier,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            items.forEach { item ->
                NavigationBarItem(
                    label = {
                        Text(text = item.label)
                    },
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.contentDescription
                        )
                    },
                    selected = currentRoute == item.route,
                    onClick = {
                        if (currentRoute != item.route)
                            navController.navigate(item.route) {
                                navController.graph.startDestinationRoute?.let { screenRoute ->
                                    popUpTo(screenRoute) {
                                        saveState = true
                                    }
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                    },
                    colors = NavigationBarItemDefaults.colors()
                        .copy(selectedIndicatorColor = Green.copy(alpha = 0.25f))
                )
            }
        }
    }
}

sealed class BottomNavItem(
    val route: String,
    val icon: ImageVector,
    val label: String,
    val contentDescription: String,
) {
    data object Home :
        BottomNavItem(
            Screen.HomeScreen.route,
            Icons.Default.Home,
            "Home",
            "home_page"
        )

    data object Favorite :
        BottomNavItem(
            Screen.FavoriteScreen.route,
            Icons.Default.Favorite,
            "Favorite",
            "favorite_page"
        )

    data object About :
        BottomNavItem(
            Screen.AboutScreen.route,
            Icons.Default.Person,
            "About",
            "about_page"
        )
}