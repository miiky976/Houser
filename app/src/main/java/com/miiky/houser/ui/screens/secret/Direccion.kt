package com.miiky.houser.ui.screens.secret

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.miiky.houser.data.Themed
import com.miiky.houser.data.direction
import com.miiky.houser.ui.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Direccion(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
    tema: MutableState<Boolean>
) {
    val IP = remember { mutableStateOf(direction) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(value = IP.value, onValueChange = {IP.value = it}, modifier = modifier.fillMaxWidth())
        Button(onClick = { direction = IP.value }) {
            Icon(Icons.Rounded.Info, contentDescription = null)
            Text(text = "Cambiar IP")
        }
        Switch(checked = Themed.value, onCheckedChange = {Themed.value = it})
    }
}