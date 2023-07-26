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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Temperature(
    modifier: Modifier = Modifier,
) {
    val value = remember { mutableStateOf("") }
    val res = remember { mutableStateOf("") }
    val lista = listOf(
        Itemso("Celsius", 0),
        Itemso("Fahrenheit", 1),
        Itemso("Kelvin", 2)
    )
    val list = arrayOf("Celsius", "Fahrenheit", "Kelvin")
    val sel1 = remember { mutableStateOf(0) }
    val sel2 = remember { mutableStateOf(0) }
    Column() {
        Text(text = "Temperatura", style = MaterialTheme.typography.displaySmall)
        Row {
            TextField(
                value = value.value, onValueChange = { value.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            MenuButton(items = lista, select = sel1)
        }
        Row {
            TextField(
                value = res.value, onValueChange = { res.value = it },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            MenuButton(items = lista, select = sel2)
        }
        Button(onClick = {
            var input: Int
            var result: Float
//            if ()
            if (sel1.value == sel2.value) {
                res.value = value.value
            }
            if (sel1.value == 0) {
                if (sel2.value == 1) {

                }else if (sel2.value == 2) {

                }
            }
            if (sel1.value == 1) {
                if (sel2.value == 0) {

                }else if (sel2.value == 2) {

                }
            }
            if (sel1.value == 2) {
                if (sel2.value == 1) {

                }else if (sel2.value == 0) {

                }
            }
        }) {
            Text(text = "Calcular")
        }
    }
}

data class Itemso(
    val name: String,
    val item: Int,
)

@Composable
fun MenuButton(
    modifier: Modifier = Modifier,
    items: List<Itemso>, select: MutableState<Int>,
) {
    val expanded = remember { mutableStateOf(false) }
    val selected = remember { mutableStateOf(items[0].name) }
    Button(onClick = {
        expanded.value = true
    }) {
        Text(text = selected.value)
        DropdownMenu(expanded = expanded.value, onDismissRequest = { expanded.value = false }) {
            items.forEach { item ->
                DropdownMenuItem(text = { Text(text = item.name) }, onClick = {
                    expanded.value = false
                    selected.value = item.name
                    select.value = item.item
                })
            }
        }
    }
}