package com.miiky.houser.ui.screens.use

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Image
import androidx.compose.material.icons.rounded.PhotoLibrary
import androidx.compose.material.icons.rounded.Save
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.miiky.houser.R
import com.miiky.houser.data.direction
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.extras.ComposeFileProvider
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.spacing
import kotlinx.coroutines.launch
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import retrofit2.http.Multipart
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEdit(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)
    val name = dataStore.getName.collectAsState(initial = "")
    val user = dataStore.getUser.collectAsState(initial = "")
    val last = dataStore.getLastname.collectAsState(initial = "")
    val image = dataStore.getImage.collectAsState(initial = "")
    val email = dataStore.getEmail.collectAsState(initial = "")

    val status = remember { mutableStateOf("") }
    
    val namefield = remember { mutableStateOf(name.value) }
    val lastfield = remember { mutableStateOf(last.value) }
    val emailfield = remember { mutableStateOf(email.value) }
    val userfield = remember { mutableStateOf(user.value) }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val hasImage = remember { mutableStateOf(false) }

    val openDialog = remember { mutableStateOf(false) }

    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri = uri }
    )
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicture(),
        onResult = { success ->
            hasImage.value = success
        }
    )

    Column(
        modifier = modifier
            .padding()
            .verticalScroll(rememberScrollState())
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(modifier = modifier.wrapContentSize(Alignment.BottomEnd)) {
            Card(
                modifier = modifier.size(300.dp),
                shape = CircleShape
            ) {
                Box(modifier = modifier.fillMaxSize()){
                    Image(
                        painterResource(id = R.drawable.chihuahua),
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = "http://${direction.value}:3000/user/images/${image.value}",
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            FilledIconButton(onClick = {
                openDialog.value = true

            }) {
                Icon(Icons.Rounded.Edit, contentDescription = null)
            }
        }
        Column(modifier = modifier.fillMaxWidth()) {
            OutlinedTextField(value = userfield.value,
                onValueChange = { userfield.value = it },
                modifier = modifier
                    .padding(bottom = spacing)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.username)
                    )
                })
            Row {
                OutlinedTextField(
                    value = namefield.value,
                    onValueChange = { namefield.value = it },
                    modifier = modifier
                        .padding(bottom = spacing)
                        .weight(1f),
                    label = {
                        Text(
                            text = stringResource(id = R.string.name)
                        )
                    })
                FilledIconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Rounded.Edit, contentDescription = null)
                }
            }
            Row {
                OutlinedTextField(
                    value = lastfield.value,
                    onValueChange = { lastfield.value = it },
                    modifier = modifier
                        .padding(bottom = spacing)
                        .weight(1f),
                    label = {
                        Text(
                            text = stringResource(id = R.string.lastname)
                        )
                    })
                FilledIconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Rounded.Edit, contentDescription = null)
                }
            }
            OutlinedTextField(
                value = emailfield.value,
                onValueChange = { emailfield.value = it },
                modifier = modifier
                    .padding(bottom = spacing)
                    .fillMaxWidth(),
                label = {
                    Text(
                        text = stringResource(id = R.string.email)
                    )
                })
            Button(
                onClick = {
                    if (selectedImageUri == null){
                        Toast.makeText(context, "No has seleccionado una imagen", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    val url = "http://${direction.value}:3000/user/image"
                    val fileInputStream = context.contentResolver.openInputStream(selectedImageUri!!)
                    val client = OkHttpClient()
                    val body = MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("upload", "filename.png", fileInputStream!!.readBytes().toRequestBody())
                        .addFormDataPart("email", email.value)
                        .build()
                    val request = okhttp3.Request.Builder()
                        .url(url)
                        .post(body)
                        .build()

                    client.newCall(request).enqueue(object : Callback{
                        override fun onFailure(call: Call, e: IOException) {
                            TODO("Not yet implemented")
                        }

                        override fun onResponse(call: Call, response: okhttp3.Response) {
                            val responseBody = response.body?.string()
                            val json = JSONObject(responseBody!!)
                            status.value = json.optString("Status")
                            scope.launch {
                                dataStore.saveImage(status.value)
                            }
                        }
                    })

                    fileInputStream.close()
                },
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.Save, contentDescription = null)
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
    if (openDialog.value) {
        MediaSelect(openDialog = openDialog,
            photos = {
                singlePhotoPickerLauncher.launch(
                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                )
            }, camera = {
//                val uri = ComposeFileProvider.getImageUri(context)
//                selectedImageUri = uri
//                cameraLauncher.launch(uri)
            }
        )
    }
}

@Composable
fun MediaSelect(
    openDialog: MutableState<Boolean>,
    modifier: Modifier = Modifier,
    photos: () -> Unit,
    camera: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = { openDialog.value = false },
        confirmButton = { /*TODO*/ },
        icon = {
            Icon(Icons.Rounded.Image, contentDescription = null)
        },
        title = { Text(text = stringResource(id = R.string.mediaselect)) },
        text = {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                FilledIconButton(onClick = { camera() }) {
                    Icon(Icons.Rounded.AddAPhoto, contentDescription = null)
                }
                FilledIconButton(onClick = { photos() }) {
                    Icon(Icons.Rounded.PhotoLibrary, contentDescription = null)
                }
            }
        }
    )
}