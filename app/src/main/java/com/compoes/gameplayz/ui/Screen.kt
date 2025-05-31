package com.compoes.gameplayz.ui

sealed class Screen(val route: String) {
    data object HomeScreen : Screen("home_screen")
    data object FavoriteScreen : Screen("favorite_screen")
    data object AboutScreen : Screen("about_screen")
    data object DetailScreen : Screen("detail_screen")
}