package com.miiky.houser.ui.screens.session

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.miiky.houser.ui.EmailTextField
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.spacing
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Code(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current)
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val code = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(0) }
    val visible = remember { mutableStateOf(true) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Obtener codigo de recuperación")
        EmailTextField(email = email, error = error)
        Button(onClick = {
            val url = "http://${direction.value}:3000/user/recovery"

            val queue = Volley.newRequestQueue(context)
            val body = JSONObject().apply {
                put("email", email.value)
            }

            val request = JsonObjectRequest(
                Request.Method.POST, url, body,
                { response ->
                    if (response.getString("Status")=="Sent"){
                        visible.value = true
                    }else if (response.getString("Status")=="Doesnt Exists") {
                        Toast.makeText(context, "Ese correo ya existe", Toast.LENGTH_SHORT).show()
                    }
                },
                { error ->
                    // Error en la solicitud al servidor
                    // Manejar el error según sea necesario
                })
            queue.add(request)
        }) {
            Text(text = stringResource(id = R.string.getcode))
        }
        Divider()
        AnimatedVisibility(visible.value) {
            Column {
                Text(text = "Obtener codigo de recuperación")
                OutlinedTextField(value = code.value, onValueChange = {code.value = it}, singleLine = true, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number))
                Button(onClick = {
                    val url = "http://${direction.value}:3000/user/code"

                    val queue = Volley.newRequestQueue(context)
                    val body = JSONObject().apply {
                        put("email", email.value)
                        put("code", code.value)
                    }

                    val request = JsonObjectRequest(
                        Request.Method.POST, url, body,
                        { response ->
                            when(response.getString("Status")){
                                "Doesnt Exists"->{
                                    Toast.makeText(context, "No existe", Toast.LENGTH_SHORT).show()
                                }
                                "A recovery was not requested"->{
                                    Toast.makeText(context, "No se ha solicitado codigo", Toast.LENGTH_SHORT).show()
                                }
                                "Mismatch"->{
                                    Toast.makeText(context, "Codigo incorrecto", Toast.LENGTH_SHORT).show()
                                }
                                "Correct"->{
                                    navHost.navigate("login")
                                }
                            }
                        },
                        { error ->
                            // Error en la solicitud al servidor
                            // Manejar el error según sea necesario
                        })
                    queue.add(request)
                }) {
                    Text(text = stringResource(id = R.string.trycode))
                }
            }
        }
    }
}