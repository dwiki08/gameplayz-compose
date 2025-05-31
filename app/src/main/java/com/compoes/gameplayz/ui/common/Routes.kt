package com.compoes.gameplayz.ui.common

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.compoes.gameplayz.ui.Screen
import com.compoes.gameplayz.ui.screen.about.AboutScreen
import com.compoes.gameplayz.ui.screen.detail_screen.DetailScreen
import com.compoes.gameplayz.ui.screen.favorite_screen.FavoriteScreen
import com.compoes.gameplayz.ui.screen.home_screen.HomeScreen

const val ARG_GAME_ID = "id"

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier) {

    NavHost(navController = navController, startDestination = Screen.HomeScreen.route) {

        fun navigateToDetail(id: Int) {
            navController.navigate("${Screen.DetailScreen.route}/$id")
        }

        composable(route = Screen.HomeScreen.route) {
            HomeScreen(modifier) { navigateToDetail(it) }
        }

        composable(route = Screen.FavoriteScreen.route) {
            FavoriteScreen(modifier = modifier) { navigateToDetail(it) }
        }

        composable(route = Screen.AboutScreen.route) {
            AboutScreen(modifier = modifier)
        }

        composable(
            route = "${Screen.DetailScreen.route}/${ARG_GAME_ID}",
            arguments = listOf(
                navArgument(ARG_GAME_ID) { type = NavType.IntType },
            )
        ) {
            DetailScreen(
                id = it.arguments?.getInt(ARG_GAME_ID) ?: 0,
                navigateToBack = { navController.navigateUp() }
            )
        }
    }
}