package com.miiky.houser.ui.screens.crap

import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.miiky.houser.data.DatabaseHandler
import com.miiky.houser.ui.spacing
import java.util.ArrayList

@Composable
fun Todo(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    var db = DatabaseHandler(context)
    db.openDatabase()
    

    val task = remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(horizontal = spacing)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ElevatedCard(
            modifier = modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            Task(id = 1, task = "Hola", status = true)
            Task(id = 1, task = "Hola", status = true)
            Task(id = 1, task = "Hola", status = true)
            Task(id = 1, task = "Hola", status = true)
            Task(id = 1, task = "Hola", status = true)
        }
        AddTask(task = task)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTask(
    modifier: Modifier = Modifier,
    task: MutableState<String>,
) {
    Row(modifier = modifier.padding(vertical = spacing)) {
        OutlinedTextField(
            value = task.value,
            onValueChange = { task.value = it },
            shape = MaterialTheme.shapes.extraLarge,
            modifier = modifier
                .weight(1f)
                .align(Alignment.CenterVertically)
                .padding(end = spacing),
        )
        FloatingActionButton(
            onClick = { /*TODO*/ },
            modifier = modifier.align(Alignment.Bottom)
        ) {
            Icon(Icons.Rounded.Add, contentDescription = "Add new task")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun Task(
    modifier: Modifier = Modifier,
    id: Int,
    task: String,
    status: Boolean,
) {
    val stat = remember { mutableStateOf(status) }
    val edit = remember { mutableStateOf(false) }
    val text = remember { mutableStateOf(task) }
    val expanded = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = spacing)
    ) {
        Box(modifier = modifier.combinedClickable(
            onClick = {
                stat.value = !stat.value
            },
            onLongClick = {
                expanded.value = true
            }
        )) {
            Row(
                modifier = modifier.animateContentSize()
                ,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(checked = stat.value, onCheckedChange = { stat.value = it })
                Crossfade(edit.value, label = "Edible", modifier = modifier.weight(1f)) {
                    if (it) {
                        Row {
                            OutlinedTextField(
                                value = text.value,
                                onValueChange = { text.value = it },
                                modifier = modifier.weight(1f)
                            )
                            IconButton(onClick = { edit.value = false }) {
                                Icon(Icons.Rounded.Check, contentDescription = "Save changes")
                            }
                        }
                    } else {
                        Text(text = text.value, modifier = modifier.weight(1f))
                    }
                }
            }
            DropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false },
                modifier = modifier
            ) {
                DropdownMenuItem(
                    text = { Text(text = "Editar") },
                    onClick = {
                        edit.value = true
                        expanded.value = false
                    },
                    leadingIcon = { Icon(Icons.Rounded.Edit, contentDescription = null) })
                DropdownMenuItem(
                    text = { Text(text = "Eliminar") },
                    onClick = {
                        expanded.value = false
                    },
                    leadingIcon = { Icon(Icons.Rounded.Delete, contentDescription = null) })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Todoprev() {
    Todo()
}