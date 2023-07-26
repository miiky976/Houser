package com.miiky.houser.ui.screens.crap.converor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Weight(
    modifier: Modifier = Modifier,
) {
    val val1 = remember { mutableStateOf("") }
    Column() {
        Text(text = "Peso", style = MaterialTheme.typography.displaySmall)
        Row {
            TextField(value = val1.value, onValueChange = {val1.value = it})
        }
    }
}