package com.miiky.houser.ui.screens.features

import android.content.Intent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.miiky.houser.data.direction
import com.miiky.houser.data.houseId
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.models.Houses
import com.miiky.houser.ui.spacing
import kotlinx.coroutines.launch

@Composable
fun Account(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)
    val id = dataStore.getID.collectAsState(initial = "")
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val url =
            "http://${direction.value}:3000/houses/${id.value}"
        val queue = Volley.newRequestQueue(context)
        val houses = remember { mutableStateOf<List<Houses>>(emptyList()) }
        val request = StringRequest(
            Request.Method.GET, url,
            {
                val jause = Gson().fromJson(it, Array<Houses>::class.java).toList()
                houses.value = jause
            },
            {}
        )
        queue.add(request)
        for (house in houses.value) {
            MineHouseCard(house = house, navHost = navHost)
        }
    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = modifier
            .fillMaxSize()
            .padding(spacing)
    ) {
        FloatingActionButton(onClick = {
//            Toast.makeText(context, "ID: ${id.value}", Toast.LENGTH_SHORT).show()
            navHost.navigate("create")
        }, modifier = modifier) {
            Icon(Icons.Rounded.Add, contentDescription = null)
        }
    }
}

@Composable
fun MineHouseCard(
    modifier: Modifier = Modifier,
    house: Houses,
    navHost: NavHostController,
) {
    val context = LocalContext.current
    Card(
        modifier.padding(bottom = spacing)
    ) {
        Box(
            modifier = modifier.clickable {
                houseId.value = house.ID
//                Toast.makeText(context, "${houseId.value}", Toast.LENGTH_SHORT).show()
                navHost.navigate("edit")
            }
        ) {
            Column {
                AsyncImage(
                    model = "http://${direction.value}:3000/user/images/${house.img}",
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier
                        .animateContentSize()
                        .padding(spacing / 2)
                ) {
                    Text(
                        text = house.title,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                    Text(text = house.subtitle, style = MaterialTheme.typography.titleMedium)
                }
            }
        }
    }
}