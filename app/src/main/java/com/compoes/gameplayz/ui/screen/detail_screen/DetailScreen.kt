package com.compoes.gameplayz.ui.screen.detail_screen

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.compoes.gameplayz.R
import com.compoes.gameplayz.domain.model.Game
import com.compoes.gameplayz.ui.component.BackNavButton
import com.compoes.gameplayz.ui.component.LoadingDialog
import com.compoes.gameplayz.ui.component.StarButton
import com.compoes.gameplayz.ui.component.TopBar
import com.compoes.gameplayz.ui.theme.Green
import com.compoes.gameplayz.utils.ext.toEmptyOrString
import com.compoes.gameplayz.utils.ext.toLocalDate
import me.onebone.toolbar.CollapsingToolbarScaffold
import me.onebone.toolbar.ScrollStrategy
import me.onebone.toolbar.rememberCollapsingToolbarScaffoldState

@Composable
internal fun DetailScreen(id: Int, navigateToBack: () -> Unit) {
    val viewModel: DetailViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    DetailContent(
        state = state,
        navigateToBack = navigateToBack,
        addFavorite = { viewModel.addFavorite(it) },
        removeFavorite = { viewModel.removeFavorite(it) }
    )
    LaunchedEffect(true) {
        viewModel.getDetailGame(id)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DetailContent(
    state: DetailState,
    navigateToBack: () -> Unit,
    addFavorite: (game: Game) -> Unit,
    removeFavorite: (id: Int) -> Unit,
) {
    val toolbarState = rememberCollapsingToolbarScaffoldState()
    var isLoadingImage by remember {
        mutableStateOf(true)
    }
    LoadingDialog(isLoading = state.isLoading)
    CollapsingToolbarScaffold(
        state = toolbarState,
        modifier = Modifier.fillMaxSize(),
        scrollStrategy = ScrollStrategy.ExitUntilCollapsed,
        enabled = true,
        toolbar = {
            TopBar(
                modifier = Modifier.padding(start = 52.dp, end = 52.dp),
                title = state.game?.name.toEmptyOrString()
            )
            if (isLoadingImage) Spacer(modifier = Modifier.height(300.dp))
            state.game?.let { game ->
                AsyncImage(
                    model = game.posterImage,
                    contentDescription = null,
                    contentScale = ContentScale.FillWidth,
                    onSuccess = {
                        isLoadingImage = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .graphicsLayer {
                            alpha = toolbarState.toolbarState.progress
                        },
                    clipToBounds = false
                )
                TopAppBar(title = {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 12.dp)
                    ) {
                        BackNavButton(navigateToBack = navigateToBack)
                        StarButton(isChecked = game.isFavorite) {
                            if (game.isFavorite) {
                                removeFavorite(game.id)
                            } else {
                                addFavorite(game)
                            }
                        }
                    }
                }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent))
            }
        }
    ) {
        Box {
            if (!isLoadingImage) {
                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(16.dp)
                ) {
                    val alphaState by animateFloatAsState(
                        targetValue = toolbarState.toolbarState.progress,
                        label = "title_alpha"
                    )
                    Text(
                        text = state.game?.name.toEmptyOrString(),
                        modifier = Modifier.alpha(alphaState),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    val rating = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Green,
                                fontWeight = FontWeight.Bold
                            )
                        ) {
                            append(state.game?.rating.toString())
                        }
                        withStyle(
                            style = SpanStyle(
                                fontSize = 14.sp
                            )
                        ) {
                            append(" / 5.0")
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.rating),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(text = rating)

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.release_date),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = state.game?.releaseDate.toLocalDate()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.platforms),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = state.game?.platforms?.joinToString().toEmptyOrString()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.genres),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Text(
                        text = state.game?.genres.toEmptyOrString()
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = stringResource(R.string.description),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.alpha(if (state.game?.description != null) 1f else 0f)
                    )
                    Text(
                        text = state.game?.description.toEmptyOrString(),
                        textAlign = TextAlign.Justify
                    )
                }
            }
        }
    }
}