package com.miiky.houser.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.miiky.houser.R

@Composable
fun UserMain(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    Column(
        modifier = modifier
            .padding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = modifier.size(300.dp),
            shape = CircleShape
        ) {
            Image(
                painterResource(id = R.drawable.chihuahua),
                contentDescription = null,
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = modifier.fillMaxWidth()) {
            Text(
                text = "Username",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
            )
            Text(text = "Nombre", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Apellidos", style = MaterialTheme.typography.headlineSmall)
            Text(text = "Correo", style = MaterialTheme.typography.headlineSmall)
            Button(
                onClick = {
                    navHost.navigate("edit")
                },
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.Edit, contentDescription = null)
                Text(text = stringResource(id = R.string.edit))
            }
        }
    }
}