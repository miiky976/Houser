package com.miiky.houser.ui.screens.session

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.miiky.houser.R
import com.miiky.houser.data.direction
import com.miiky.houser.models.User
import com.miiky.houser.ui.EmailTextField
import com.miiky.houser.ui.InputTextField
import com.miiky.houser.ui.PasswordTexField
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.space_top
import com.miiky.houser.ui.spacing
import org.json.JSONObject

@Composable
fun Signup(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current)
){
    val context = LocalContext.current
    val user = remember { mutableStateOf("") }
    val user_error = remember { mutableStateOf(0) }
    val email = remember { mutableStateOf("") }
    val email_error = remember { mutableStateOf(0) }
    val name = remember { mutableStateOf("") }
    val name_error = remember { mutableStateOf(0) }
    val lastname = remember { mutableStateOf("") }
    val last_error = remember { mutableStateOf(0) }
    val password1 = remember { mutableStateOf("") }
    val password1_error = remember { mutableStateOf(0) }
    val password2 = remember { mutableStateOf("") }
    val password2_error = remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(id = R.string.signup),
            style = MaterialTheme.typography.displaySmall,
            modifier = modifier.padding(bottom = spacing, top = space_top)
        )
        InputTextField(value = user, error = user_error, label = stringResource(id = R.string.username))
        EmailTextField(email = email, error = email_error)
        PasswordTexField(pass = password1, error = password1_error, modifier = modifier.padding(bottom = spacing))
        PasswordTexField(pass = password2, error = password2_error, modifier = modifier.padding(bottom = spacing))
        InputTextField(value = name, error = name_error, label = stringResource(id = R.string.name))
        InputTextField(value = lastname, error = last_error, label = stringResource(id = R.string.lastname))
        Button(onClick = {
            if (user_error.value != 0 || email_error.value != 0 || password1_error.value != 0 || password2_error.value != 0 || name_error.value != 0 || last_error.value != 0){
                return@Button
            }
            var error = 0
            if (user.value.isEmpty()){
                user_error.value = 1
                error += 1
            }
            if (email.value.isEmpty()){
                email_error.value = 1
                error += 1
            }
            if (password1.value.isEmpty()){
                password1_error.value = 1
                error += 1
            }
            if (password2.value.isEmpty()){
                password2_error.value = 1
                error += 1
            }
            if (name.value.isEmpty()){
                name_error.value = 1
                error += 1
            }
            if (lastname.value.isEmpty()){
                last_error.value = 1
                error += 1
            }
            if (error != 0){
                return@Button
            }
            if (password1.value != password2.value){
                password2_error.value = 3
                return@Button
            }
            var url = "http://$direction:3000/users"

            var queue = Volley.newRequestQueue(context)

            var usr = User(email = email.value, username = user.value, pass = hashString(password1.value), name = name.value, lastname = lastname.value)
            val request = JsonObjectRequest(
                Request.Method.POST, url, JSONObject(Gson().toJson(usr)),
                { response ->
                    if (response == null){
                        return@JsonObjectRequest
                    }
                    else{
                        Toast.makeText(context, "Usuario insertado", Toast.LENGTH_SHORT).show()
                        navHost.navigate("login")
                    }
                },
                { error ->
                    // Error en la solicitud al servidor
                    // Manejar el error según sea necesario
                })
            queue.add(request)
        },
            modifier = modifier
                .padding(bottom = space_top)
                .fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.signup))
        }
    }
}