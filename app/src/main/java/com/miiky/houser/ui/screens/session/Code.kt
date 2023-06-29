package com.miiky.houser.ui.screens.session

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.miiky.houser.data.direction
import com.miiky.houser.ui.EmailTextField
import com.miiky.houser.ui.spacing
import org.json.JSONObject

@Composable
fun Code(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current)
) {
    val context = LocalContext.current
    val email = remember { mutableStateOf("") }
    val error = remember { mutableStateOf(0) }
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
            var url = "http://$direction:3000/recovery/${email.value}"

            var queue = Volley.newRequestQueue(context)

            val request = JsonObjectRequest(
                Request.Method.POST, url, JSONObject(),
                { response ->
                    if (response == null){
                        return@JsonObjectRequest
                    }
                    else{
                        Toast.makeText(context, "Codigo enviado", Toast.LENGTH_SHORT).show()
                        navHost.navigate("login")
                    }
                },
                { error ->
                    // Error en la solicitud al servidor
                    // Manejar el error según sea necesario
                })
            queue.add(request)
        }) {

        }
    }
}