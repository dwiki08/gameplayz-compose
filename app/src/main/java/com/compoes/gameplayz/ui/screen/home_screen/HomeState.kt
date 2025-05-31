package com.compoes.gameplayz.ui.screen.home_screen

import com.compoes.gameplayz.domain.model.Game

data class HomeState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val query: String = "",
    val games: List<Game> = listOf()
)