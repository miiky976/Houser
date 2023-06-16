package com.miiky.houser.ui.screens.sesion

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.miiky.houser.R
import com.miiky.houser.ui.EmailTextField
import com.miiky.houser.ui.PasswordTexField
import com.miiky.houser.ui.direccion
import com.miiky.houser.ui.hashString
import com.miiky.houser.ui.spacing
import com.miiky.houser.ui.theme.HouserTheme
import org.json.JSONException

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Login(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current)
) {
    val context = LocalContext.current
    val color = MaterialTheme.colorScheme.secondary

    val email = remember { mutableStateOf("") }
    val email_error = remember { mutableStateOf(0) }
    val password = remember { mutableStateOf("") }
    val password_error = remember { mutableStateOf(0) }
    Column (
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
                            .makeText(context, direccion, Toast.LENGTH_SHORT)
                            .show()
                    },
                    onLongClick = { navHost.navigate("direccion") },
                    onDoubleClick = {navHost.navigate("master")}
                ),
        )
        EmailTextField(email = email, error = email_error, modifier = modifier.padding(bottom = spacing))
        PasswordTexField(pass = password, error = password_error, modifier = modifier.padding(bottom = 0.dp))
        Row (modifier = modifier
            .fillMaxWidth()
            .padding(bottom = spacing), Arrangement.End) {
            TextButton(onClick = { navHost.navigate("code") }) { Text(text = stringResource(id = R.string.forgot)) }
        }
        Row (
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ElevatedButton(onClick = {
                navHost.navigate("signup")
            }, modifier = modifier.weight(1f)) {
                Text(text = stringResource(id = R.string.signup))
            }
            Button(onClick = {
                if (email_error.value != 0 || password_error.value != 0){
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
                if (password.value.isEmpty()){
                    password_error.value = 1
                    return@Button
                }
                var url = "http://$direccion:3000/usere/${email.value}"

                var queue = Volley.newRequestQueue(context)

                val request = JsonObjectRequest(Request.Method.GET, url, null,
                    { response ->
                        try {
                            val storedPassword = response.getString("Pass")

                            if (hashString(password.value) == storedPassword) {
                                navHost.navigate("master")
                            } else {
                                Toast.makeText(context, "Contraseña no valida", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: JSONException) {
                            e.printStackTrace()
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

@Preview(showBackground = true, )
@Composable
fun GreetingPreview() {
    HouserTheme {
        Login()
    }
}