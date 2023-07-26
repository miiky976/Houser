package com.miiky.houser.ui.screens.session

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.miiky.houser.R
import com.miiky.houser.data.loginConnect
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.data.direction
import com.miiky.houser.ui.EmailTextField
import com.miiky.houser.ui.PasswordTexField
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.spacing
import com.miiky.houser.ui.theme.HouserTheme
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)
    val response = MutableLiveData("")
    val res = remember { mutableStateOf("") }

    val color = MaterialTheme.colorScheme.secondary

    val email = remember { mutableStateOf("") }
    val email_error = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf("") }
    val password_error = remember { mutableStateOf(0) }

    val missed = stringResource(id = R.string.password_missed)
    val notexists = stringResource(id = R.string.notexists)
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.login),
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier
                .padding(bottom = spacing)
                .combinedClickable(
                    onClick = {
                        Toast
                            .makeText(context, direction.value, Toast.LENGTH_SHORT)
                            .show()
                    },
                    onLongClick = { navHost.navigate("direccion") },
                    onDoubleClick = { navHost.navigate("master") }
                ),
        )
        EmailTextField(
            email = email,
            error = email_error,
            modifier = modifier.padding(bottom = spacing)
        )
        PasswordTexField(
            pass = password,
            error = password_error,
            modifier = modifier.padding(bottom = 0.dp)
        )
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = spacing), Arrangement.End
        ) {
            TextButton(onClick = { navHost.navigate("code") }) { Text(text = stringResource(id = R.string.forgot)) }
        }
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ElevatedButton(onClick = {
                navHost.navigate("signup")
            }, modifier = modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.signup))
            }
            Button(
                onClick = {
                    if (email_error.value != 0 || password_error.value != 0) {
                        return@Button
                    }
                    if (email.value.isEmpty() && password.value.isEmpty()) {
                        email_error.value = 2
                        password_error.value = 1
                        return@Button
                    }
                    if (email.value.isEmpty()) {
                        email_error.value = 2
                        return@Button
                    }
                    if (password.value.isEmpty()) {
                        password_error.value = 1
                        return@Button
                    }
//                    loginConnect(context, email.value, hashString(password.value), response)
//                    response.observe(context as LifecycleOwner){
//                        when (it){
//                            "Correct"-> {
//                                scope.launch {
//                                    dataStore.saveSession(email.value)
//                                }
//                                navHost.navigate("master")
//                            }
//                            "Incorrect"->{
//                                Toast.makeText(context, missed, Toast.LENGTH_SHORT).show()
//                            }
//                            "Doesnt Exists"->{
//                                Toast.makeText(context, notexists, Toast.LENGTH_SHORT).show()
//                            }
////                            else-> {
////                                Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
////                            }
//                        }
//                    }
                    val url = "http://${direction.value}:3000/user/data"
                    val queue = Volley.newRequestQueue(context)
                    val body = JSONObject().apply {
                        put("email", email.value)
                        put("pass", hashString(password.value))
                    }
                    val request = JsonObjectRequest(
                        Request.Method.POST, url, body,
                        { response ->
                            if (response.has("Status")){
                                val status = response.getString("Status")

                                when (status) {
                                    "Incorrect" -> {
                                        Toast.makeText(context, missed, Toast.LENGTH_SHORT).show()
                                    }
                                    "Doesnt Exists" -> {
                                        Toast.makeText(context, notexists, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }else{
                                val user = response.getString("user")
                                val name = response.getString("name")
                                val last = response.getString("last")
                                val image = response.getString("Image")
                                val id = response.getString("ID")
                                scope.launch {
                                    dataStore.saveSession(email.value)
                                    dataStore.saveName(name)
                                    dataStore.saveUser(user)
                                    dataStore.saveLastname(last)
                                    dataStore.saveImage(image)
                                    dataStore.saveID(id)
                                }
                                Toast.makeText(context, "El ID es: $id", Toast.LENGTH_SHORT).show()
                                navHost.navigate("master")
                            }
                        },
                        { error ->
                            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
                        })
                    queue.add(request)
                }, modifier = modifier.weight(1f)
            ) {
                Text(text = stringResource(id = R.string.login))
            }
        }
    }
}

//fun send(id: String)

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    HouserTheme {
        Login()
    }
}