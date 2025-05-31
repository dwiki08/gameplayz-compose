package com.compoes.gameplayz.ui.screen.about

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.core.net.toUri
import com.compoes.gameplayz.R
import com.compoes.gameplayz.ui.theme.GamePlayzTheme


@Composable
fun AboutScreen(modifier: Modifier) {
    AboutContent(modifier = modifier)
}

@Composable
fun AboutContent(modifier: Modifier) {
    val context = LocalContext.current
    Scaffold(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding(), start = 16.dp, end = 16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.profile),
                modifier = Modifier
                    .size(150.dp)
                    .clip(shape = CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary),
                contentDescription = "about",
                contentScale = ContentScale.FillWidth
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Yohanes Dwiki Kurniawan", style = MaterialTheme.typography.titleLarge)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Icon(
                    imageVector = Icons.Default.Email,
                    contentDescription = "email",
                    Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    modifier = Modifier.clickable {
                        val intent =
                            Intent(Intent.ACTION_SENDTO, "mailto:yohanesdwiki08@gmail.com".toUri())
                        startActivity(context, intent, null)
                    },
                    text = "yohanesdwiki08@gmail.com",
                    style = MaterialTheme.typography.labelMedium.copy(textDecoration = TextDecoration.Underline)
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    GamePlayzTheme {
        AboutContent(modifier = Modifier)
    }
}