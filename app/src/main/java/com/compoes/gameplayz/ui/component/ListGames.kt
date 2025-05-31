package com.compoes.gameplayz.ui.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.compoes.gameplayz.domain.model.Game

@Composable
fun ListGames(
    modifier: Modifier = Modifier,
    games: List<Game> = emptyList(),
    lazyListState: LazyListState = rememberLazyListState(),
    showLoading: Boolean = false,
    navigateToDetail: (id: Int) -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        LazyColumn(
            state = lazyListState,
            contentPadding = PaddingValues(16.dp)
        ) {
            items(count = games.size, key = { i -> games[i].id }) { i ->
                CardGame(
                    modifier = Modifier.fillMaxWidth(),
                    game = games[i],
                    navigateToDetail = { id ->
                        navigateToDetail(id)
                    }
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
            if (showLoading) {
                item {
                    CardGameShimmer(modifier = Modifier.fillMaxWidth())
                }
            }
        }
    }
}