package com.compoes.gameplayz.ui.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.compoes.gameplayz.R
import com.compoes.gameplayz.domain.model.Game
import com.compoes.gameplayz.ui.theme.Green
import com.compoes.gameplayz.utils.ext.toLocalDate

@Composable
fun CardGame(
    modifier: Modifier = Modifier,
    game: Game,
    navigateToDetail: (id: Int) -> Unit,
) {
    val isDarkTheme = isSystemInDarkTheme()
    val textValueColor = if (isDarkTheme) Color.White else Color.Black
    Card(
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.clickable {
                navigateToDetail(game.id)
            }
        ) {
            AsyncImage(
                model = game.posterImage,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth(),
                clipToBounds = false,
                contentScale = ContentScale.FillWidth
            )

            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {
                    if (game.platforms.contains(Game.Platform.PC)) {
                        PlatformIcon(R.drawable.ic_platform_windows)
                    }
                    if (game.platforms.contains(Game.Platform.XBOX)) {
                        PlatformIcon(R.drawable.ic_platform_xbox)
                    }
                    if (game.platforms.contains(Game.Platform.MAC_OS)) {
                        PlatformIcon(R.drawable.ic_platform_macos)
                    }
                    if (game.platforms.contains(Game.Platform.NINTENDO)) {
                        PlatformIcon(R.drawable.ic_platform_nintendo)
                    }
                    if (game.platforms.contains(Game.Platform.PLAYSTATION)) {
                        PlatformIcon(R.drawable.ic_platform_playstation)
                    }
                }
                val rating = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = textValueColor,
                            fontWeight = FontWeight.Bold
                        )
                    ) {
                        append(game.rating.toString())
                    }
                    withStyle(
                        style = SpanStyle(
                            fontSize = 14.sp
                        )
                    ) {
                        append(" / 5.0")
                    }
                }
                Text(text = rating)
            }

            Text(
                text = game.name,
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .fillMaxWidth(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Green,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.release_date),
                    fontSize = 14.sp
                )
                Text(
                    text = game.releaseDate.toLocalDate(),
                    color = textValueColor,
                    textAlign = TextAlign.End
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = stringResource(R.string.genres),
                    fontSize = 14.sp
                )
                Text(
                    text = game.genres,
                    color = textValueColor,
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun PlatformIcon(@DrawableRes drawable: Int) {
    val isDarkTheme = isSystemInDarkTheme()
    val iconTint = if (isDarkTheme) Color.LightGray else Color.DarkGray
    Icon(
        painter = painterResource(id = drawable),
        contentDescription = null,
        modifier = Modifier.size(20.dp),
        tint = iconTint
    )
    Spacer(modifier = Modifier.width(8.dp))
}