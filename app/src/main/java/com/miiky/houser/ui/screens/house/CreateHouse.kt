package com.miiky.houser.ui.screens.house

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Houseboat
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.OtherHouses
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material.icons.rounded.Rule
import androidx.compose.material.icons.rounded.TempleHindu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
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
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateHouse(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)

    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )

    val id = dataStore.getID.collectAsState(initial = "")

    val created = remember { mutableStateOf(false) }

    val title = remember { mutableStateOf("title") }
    val title_error = remember { mutableStateOf(0) }
    val subtitle = remember { mutableStateOf("subtitle") }
    val subtitle_error = remember { mutableStateOf(0) }
    val location = remember { mutableStateOf("location") }
    val location_error = remember { mutableStateOf(0) }
    val legal = remember { mutableStateOf("legal") }
    val legal_error = remember { mutableStateOf(0) }
    val rooms = remember { mutableStateOf(3) }
    val rooms_error = remember { mutableStateOf(0) }
    val floors = remember { mutableStateOf(1) }
    val floors_error = remember { mutableStateOf(0) }
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
                modifier = modifier.weight(1f).padding(end = spacing/2),
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
                modifier = modifier.weight(1f).padding(start = spacing/2),
                label = {
                    Text(text = "Pisos")
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        // TODO: Add image picker
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp)
                .padding(top = spacing)
        ) {
            Box(modifier = modifier.fillMaxSize().clickable {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }
            ){
                Icon(Icons.Rounded.OtherHouses, contentDescription = null, modifier = modifier.align(Alignment.Center))
                AsyncImage(
                    model = selectedImageUri,
                    contentDescription = null,
                    modifier = modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        Button(
            onClick = {
                if (title_error.value != 0 || subtitle_error.value != 0 || location_error.value != 0 || legal_error.value != 0) {
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
                if (error != 0) {
                    return@Button
                }

                val url = "http://${direction.value}:3000/house/"

                val fileInputStream = context.contentResolver.openInputStream(selectedImageUri!!)

                val client = OkHttpClient()
                val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                    .addFormDataPart("upload", "filename.png", fileInputStream!!.readBytes().toRequestBody())
                    .addFormDataPart("title", title.value)
                    .addFormDataPart("subtitle", subtitle.value)
                    .addFormDataPart("location", location.value)
                    .addFormDataPart("LegalStatus", legal.value)
                    .addFormDataPart("rooms", rooms.value.toString())
                    .addFormDataPart("floors", floors.value.toString())
                    .addFormDataPart("UserID", id.value)
                    .build()
                val request = okhttp3.Request.Builder()
                    .url(url)
                    .post(body)
                    .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        TODO("Not yet implemented")
                    }

                    override fun onResponse(call: Call, response: okhttp3.Response) {
                        val responseBody = response.body?.string()
                        val json = JSONObject(responseBody!!)
                        val status = json.optString("Status")
                        if (status == "Created") {
                            created.value = true
                        } else if (status == "Exists") {
                            Toast.makeText(context, "Ya existe", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
                fileInputStream.close()
                navHost.navigate("home")
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