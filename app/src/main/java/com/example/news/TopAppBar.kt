package com.example.news

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopAppBar(
    logo: Painter,
    title: String,
    modifier: Modifier = Modifier,
    showLogout: Boolean = false,
    onLogoutClick: (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier.height(100.dp),
        title = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = logo,
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(48.dp)
                        .padding(end = 16.dp),
                    contentScale = ContentScale.Fit
                )
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineLarge.copy(color= MaterialTheme.colorScheme.primary, fontSize = 40.sp)
                )
            }
        },
        actions = {
            if (showLogout && onLogoutClick != null) {
                IconButton(onClick = onLogoutClick) {
                    Icon(painterResource(R.drawable.baseline_logout_24), contentDescription = "Logout")
                }
            }
        }
    )
}


