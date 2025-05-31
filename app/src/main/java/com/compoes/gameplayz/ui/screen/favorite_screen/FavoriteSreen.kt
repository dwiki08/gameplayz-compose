package com.compoes.gameplayz.ui.screen.favorite_screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compoes.gameplayz.R
import com.compoes.gameplayz.ui.component.FillerView
import com.compoes.gameplayz.ui.component.ListGames
import com.compoes.gameplayz.ui.component.TopBar

@Composable
internal fun FavoriteScreen(modifier: Modifier, navigateToDetail: (id: Int) -> Unit) {
    val viewModel: FavoriteViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    FavoriteContent(modifier = modifier, state = state, navigateToDetail = navigateToDetail)
    viewModel.getFavorites()
}

@Composable
private fun FavoriteContent(modifier: Modifier, state: FavoriteState, navigateToDetail: (id: Int) -> Unit) {
    Scaffold(
        modifier = modifier,
        topBar = {
            TopBar(modifier = Modifier, title = stringResource(R.string.favorite))
        }
    ) {
        Box(modifier = Modifier.padding(top = it.calculateTopPadding())) {
            if (state.games.isEmpty()) {
                FillerView(text = stringResource(R.string.desc_no_favorite))
            } else {
                ListGames(
                    games = state.games,
                    navigateToDetail = navigateToDetail
                )
            }
        }
    }
}