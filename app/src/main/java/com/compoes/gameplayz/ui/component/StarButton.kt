package com.compoes.gameplayz.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.compoes.gameplayz.ui.theme.DarkGray
import com.compoes.gameplayz.ui.theme.Green


@Composable
fun StarButton(modifier: Modifier = Modifier, isChecked: Boolean, onClick: () -> Unit) {
    var starState by remember {
        mutableStateOf(isChecked)
    }
    IconButton(
        onClick = {
            starState = starState.not()
            onClick()
        },
        colors = IconButtonDefaults.iconButtonColors(containerColor = DarkGray)
    ) {
        Icon(
            imageVector = if (starState) {
                Icons.Default.Favorite
            } else {
                Icons.Default.FavoriteBorder
            },
            contentDescription = null,
            tint = Green,
            modifier = modifier
        )
    }
}