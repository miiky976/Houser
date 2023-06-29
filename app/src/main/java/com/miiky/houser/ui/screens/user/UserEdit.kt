package com.miiky.houser.ui.screens.use

import android.net.Uri
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.miiky.houser.R
import com.miiky.houser.extras.ComposeFileProvider
import com.miiky.houser.ui.spacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserEdit(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val name = remember { mutableStateOf("") }
    val last = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val user = remember { mutableStateOf("") }
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
                Image(
                    painterResource(id = R.drawable.chihuahua),
                    contentDescription = null,
                    modifier = modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            FilledIconButton(onClick = {
                openDialog.value = true

            }) {
                Icon(Icons.Rounded.Edit, contentDescription = null)
            }
        }
        Column(modifier = modifier.fillMaxWidth()) {
            OutlinedTextField(value = user.value,
                onValueChange = { user.value = it },
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
                    value = name.value,
                    onValueChange = { name.value = it },
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
                    value = last.value,
                    onValueChange = { last.value = it },
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
                value = email.value,
                onValueChange = { email.value = it },
                modifier = modifier.padding(bottom = spacing),
                label = {
                    Text(
                        text = stringResource(id = R.string.email)
                    )
                })
            Button(
                onClick = {
                    navHost.navigate("main")
                },
                modifier = modifier.fillMaxWidth()
            ) {
                Icon(Icons.Rounded.Save, contentDescription = null)
                Text(text = stringResource(id = R.string.save))
            }
        }
        AsyncImage(
            model = selectedImageUri,
            contentDescription = null,
            modifier = modifier.size(300.dp),
            contentScale = ContentScale.Crop
        )
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