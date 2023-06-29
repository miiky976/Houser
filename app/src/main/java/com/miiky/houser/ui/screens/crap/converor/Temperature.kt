package com.miiky.houser.ui.screens.crap.converor

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Temperature(
    modifier: Modifier = Modifier,
) {
    val val1 = remember { mutableStateOf("") }
    val list = arrayOf("Celsius", "Fahrenheit", "Kelvin")
    Column() {
        Text(text = "Temperatura", style = MaterialTheme.typography.displaySmall)
        Row {
            TextField(value = val1.value, onValueChange = { val1.value = it }, keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ))
            MenuButton(list = list)
        }
        Row {
            TextField(value = val1.value, onValueChange = { val1.value = it })
            MenuButton(list = list)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Menud(
    modifier: Modifier = Modifier,
    list: Array<String>
) {
    val expanded = remember { mutableStateOf(false) }
    val selected = remember { mutableStateOf(list[0]) }
    Box {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = { expanded.value = it },
            modifier = modifier
        ) {
            TextField(
                value = selected.value,
                onValueChange = {},
                readOnly = true,
                label = { Text(text = "Temperatura 1") },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value)
                },
            )
            ExposedDropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
                list.forEach { item ->
                    DropdownMenuItem(text = {
                        Text(text = item)
                    }, onClick = {
                        selected.value = item
                        expanded.value = false
                    })
                }
            }
        }
    }
}

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    list: Array<String>
) {
    val expanded = remember { mutableStateOf(false) }
    val selected = remember { mutableStateOf(list[0]) }
    Button(onClick = {
        expanded.value = true
    }) {
        Text(text = selected.value)
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            list.forEach { item ->
                DropdownMenuItem(text = { Text(text = item) }, onClick = {
                    expanded.value = false
                    selected.value = item
                })
            }
        }
    }
}