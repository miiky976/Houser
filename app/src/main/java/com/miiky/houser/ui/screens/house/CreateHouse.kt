package com.miiky.houser.ui.screens.house

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Houseboat
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Rule
import androidx.compose.material.icons.rounded.TempleHindu
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.miiky.houser.R
import com.miiky.houser.data.direction
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.ui.InputTextField
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.space_top
import com.miiky.houser.ui.spacing
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHouse(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)

    val id = dataStore.getID.collectAsState(initial = "")

    val title = remember { mutableStateOf("test") }
    val title_error = remember { mutableStateOf(0) }
    val subtitle = remember { mutableStateOf("test") }
    val subtitle_error = remember { mutableStateOf(0) }
    val location = remember { mutableStateOf("test") }
    val location_error = remember { mutableStateOf(0) }
    val rooms = remember { mutableStateOf(10) }
    val rooms_error = remember { mutableStateOf(20) }
    val floors = remember { mutableStateOf(1) }
    val floors_error = remember { mutableStateOf(0) }
    val legal = remember { mutableStateOf("test") }
    val legal_error = remember { mutableStateOf(0) }
    val image =
        remember { mutableStateOf("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSgdK6Nwr0Ot2wlkwQyZ-u3xyB5A_dEIYNdYA&usqp=CAU") }
    val image_error = remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = "Agregar casa",
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier.padding(bottom = spacing, top = space_top)
        )
        InputTextField(
            value = title,
            error = title_error,
            label = "Titulo",
            icon = Icons.Rounded.TempleHindu
        )
        InputTextField(
            value = subtitle,
            error = subtitle_error,
            label = "Subitulo",
            icon = Icons.Rounded.Houseboat
        )
        InputTextField(
            value = location,
            error = location_error,
            label = "Ubicacion",
            icon = Icons.Rounded.LocationOn
        )
        InputTextField(
            value = legal,
            error = legal_error,
            label = "Estatus legal",
            icon = Icons.Rounded.Rule
        )
        InputTextField(
            value = image,
            error = image_error,
            label = "URL de Imagen",
            icon = Icons.Rounded.Image
        )
        Row(
            modifier = modifier.fillMaxWidth()
        ) {
            OutlinedTextField(value = rooms.value.toString(), onValueChange = {},
                leadingIcon = {
                    IconButton(onClick = {
                        if (rooms.value == 1){
                            return@IconButton
                        }
                        rooms.value -= 1
                    }) {
                        Icon(Icons.Rounded.Remove, contentDescription = null)
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { rooms.value += 1 }) {
                        Icon(Icons.Rounded.Add, contentDescription = null)
                    }
                },
                modifier = modifier.weight(1f),
                label = {
                    Text(text = "Habitaciones")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            OutlinedTextField(value = floors.value.toString(), onValueChange = {},
                leadingIcon = {
                    IconButton(onClick = {
                        if (floors.value == 1){
                            return@IconButton
                        }
                        floors.value -= 1
                    }) {
                        Icon(Icons.Rounded.Remove, contentDescription = null)
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { floors.value += 1 }) {
                        Icon(Icons.Rounded.Add, contentDescription = null)
                    }
                },
                modifier = modifier.weight(1f),
                label = {
                    Text(text = "Pisos")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Button(
            onClick = {
                if (title_error.value != 0 || subtitle_error.value != 0 || location_error.value != 0 || legal_error.value != 0 || image_error.value != 0) {
                    return@Button
                }
                var error = 0
                if (title.value.isEmpty()) {
                    title_error.value = 1
                    error += 1
                }
                if (subtitle.value.isEmpty()) {
                    subtitle_error.value = 1
                    error += 1
                }
                if (location.value.isEmpty()) {
                    location_error.value = 1
                    error += 1
                }
                if (rooms.value <= 0) {
                    rooms_error.value = 1
                    error += 1
                }
                if (floors.value <= 0) {
                    floors_error.value = 1
                    error += 1
                }
                if (legal.value.isEmpty()) {
                    legal_error.value = 1
                    error += 1
                }
                if (image.value.isEmpty()) {
                    image_error.value = 1
                    error += 1
                }
                if (error != 0) {
                    return@Button
                }

                val url = "http://${direction.value}:3000/house/"

                val queue = Volley.newRequestQueue(context)
                val body = JSONObject().apply {
                    put("title", title.value)
                    put("subtitle", subtitle.value)
                    put("location", location.value)
                    put("rooms", rooms.value)
                    put("floors", floors.value)
                    put("legal", legal.value)
                    put("img", image.value)
                    put("UserID", id.value.toInt())
                }

                val request = JsonObjectRequest(
                    Request.Method.POST, url, body,
                    { response ->
                        if (response.getString("Status") == "Created") {
                            navHost.navigate("home")
                        } else if (response.getString("Status") == "Exists") {
                            Toast.makeText(context, "Ya existe", Toast.LENGTH_SHORT).show()
                        }
                    },
                    { error ->
                        Toast.makeText(context, "error ${error.toString()}", Toast.LENGTH_SHORT)
                            .show()
//                    if (error.equals(500)){
//                        Toast.makeText(context, "No se encontro el recurso", Toast.LENGTH_SHORT).show()
//                    }
                    })
                queue.add(request)
                Toast.makeText(context, "ID: ${id.value}", Toast.LENGTH_SHORT).show()
            },
            modifier = modifier
                .padding(bottom = space_top)
                .fillMaxWidth()
        ) {
            Icon(Icons.Rounded.Add, contentDescription = null)
            Text(text = "Agregar casa")
        }
    }
}