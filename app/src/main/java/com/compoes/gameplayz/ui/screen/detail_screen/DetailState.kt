package com.compoes.gameplayz.ui.screen.detail_screen

import androidx.compose.runtime.Stable
import com.compoes.gameplayz.domain.model.Game

@Stable
data class DetailState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val game: Game? = null
)