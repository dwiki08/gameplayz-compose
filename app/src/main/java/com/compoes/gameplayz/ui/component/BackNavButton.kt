package com.compoes.gameplayz.ui.component

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.compoes.gameplayz.ui.theme.DarkGray

@Composable
fun BackNavButton(modifier: Modifier = Modifier, navigateToBack: () -> Unit) {
    IconButton(
        onClick = {
            navigateToBack()
        },
        colors = IconButtonDefaults.iconButtonColors(containerColor = DarkGray)
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back Button",
            tint = Color.White,
            modifier = modifier
        )
    }
}
