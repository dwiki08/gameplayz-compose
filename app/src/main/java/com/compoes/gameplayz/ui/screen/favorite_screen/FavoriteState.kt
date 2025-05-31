package com.compoes.gameplayz.ui.screen.favorite_screen

import com.compoes.gameplayz.domain.model.Game

data class FavoriteState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val games: List<Game> = listOf()
)