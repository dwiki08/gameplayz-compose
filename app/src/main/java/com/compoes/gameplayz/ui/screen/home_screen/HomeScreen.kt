package com.compoes.gameplayz.ui.screen.home_screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.compoes.gameplayz.R
import com.compoes.gameplayz.ui.common.LazyListOnNearBottomHandler
import com.compoes.gameplayz.ui.component.ErrorView
import com.compoes.gameplayz.ui.component.ListGames
import com.compoes.gameplayz.ui.component.NotFoundView
import com.compoes.gameplayz.ui.theme.DarkGray
import com.compoes.gameplayz.ui.theme.Green
import com.compoes.gameplayz.ui.theme.LightGreen
import kotlinx.coroutines.launch

@Composable
internal fun HomeScreen(modifier: Modifier, navigateToDetail: (id: Int) -> Unit) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    HomeContent(
        state = state,
        modifier = modifier,
        navigateToDetail = navigateToDetail,
        setQuery = { viewModel.setStateQuery(it) },
        getGames = { viewModel.getGames() },
        nextPage = { viewModel.nextPage() },
        searchGames = {
            viewModel.searchGames(query = it)
        }
    )
}

@Composable
private fun HomeContent(
    state: HomeState,
    modifier: Modifier,
    navigateToDetail: (id: Int) -> Unit,
    setQuery: (query: String) -> Unit,
    getGames: () -> Unit,
    nextPage: () -> Unit = {},
    searchGames: (query: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()

    Scaffold(
        modifier = modifier,
        topBar = {
            Box(modifier = Modifier.padding(top = 16.dp, bottom = 16.dp, end = 16.dp)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    AnimatedVisibility(visible = state.query.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .clip(
                                    RoundedCornerShape(
                                        topEnd = 12.dp,
                                        bottomEnd = 12.dp
                                    )
                                )
                                .background(
                                    color = if (isSystemInDarkTheme()) LightGreen else DarkGray
                                )
                                .padding(horizontal = 16.dp)
                                .height(56.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = stringResource(id = R.string.app_name),
                                color = Green,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        value = state.query,
                        onValueChange = { scope.launch { setQuery(it) } },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        placeholder = { Text(text = stringResource(R.string.search_hint)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = "search"
                            )
                        },
                        trailingIcon = {
                            if (state.query.isNotEmpty())
                                Icon(
                                    modifier = Modifier.clickable {
                                        searchGames("")
                                    },
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "clear"
                                )
                        },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            scope.launch {
                                searchGames(state.query)
                            }
                        }),
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
        ) {
            if (state.error != null && state.isLoading.not() && state.games.isEmpty()) {
                ErrorView(
                    msg = state.error,
                    onClick = {
                        scope.launch {
                            if (state.query.isEmpty()) {
                                getGames()
                            } else {
                                searchGames(state.query)
                            }
                        }
                    }
                )
            } else if (state.games.isEmpty() && state.isLoading.not()) {
                NotFoundView(
                    text = stringResource(R.string.games_not_found),
                    onClick = { searchGames(state.query) })
            } else {
                ListGames(
                    modifier = Modifier,
                    games = state.games,
                    lazyListState = listState,
                    navigateToDetail = navigateToDetail,
                    showLoading = state.isLoading
                )
            }
        }
    }
    LazyListOnNearBottomHandler(
        listState = listState,
        onLoadMore = {
            nextPage()
        },
    )
}