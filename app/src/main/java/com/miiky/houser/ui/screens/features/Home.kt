package com.miiky.houser.ui.screens.features

import android.net.Uri
import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import com.miiky.houser.R
import com.miiky.houser.data.direction
import com.miiky.houser.models.Houses
import com.miiky.houser.ui.spacing

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val url = "http://${direction.value}:3000/houses"
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
        for (house in houses.value){
            HouseCard(house = house)
        }
//        HouseCard(image = "https://i.ytimg.com/vi/YiAe8EiFHOQ/maxresdefault.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://i.ytimg.com/vi/kkBcKF1sspg/maxresdefault.jpg", title = "PIRATAS - Magia, Casas, Evento y Mucho mas!")
//        HouseCard(image = "https://static.planetminecraft.com/files/resource_media/screenshot/1342/2013-10-19_155537_6566217.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://pbs.twimg.com/media/CsnqJCtWcAE85R1?format=jpg&name=4096x4096", title = "Planeta vegetta")
//        HouseCard(image = "https://i.ytimg.com/vi/Iu8a9LHBwRA/maxresdefault.jpg", title = "V DE VEGETTA: CASA ARBOL ACUATICA")
//        HouseCard(image = "https://i.ytimg.com/vi/YiAe8EiFHOQ/maxresdefault.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://i.ytimg.com/vi/kkBcKF1sspg/maxresdefault.jpg", title = "PIRATAS - Magia, Casas, Evento y Mucho mas!")
//        HouseCard(image = "https://static.planetminecraft.com/files/resource_media/screenshot/1342/2013-10-19_155537_6566217.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://pbs.twimg.com/media/CsnqJCtWcAE85R1?format=jpg&name=4096x4096", title = "Planeta vegetta")
//        HouseCard(image = "https://i.ytimg.com/vi/Iu8a9LHBwRA/maxresdefault.jpg", title = "V DE VEGETTA: CASA ARBOL ACUATICA")
//        HouseCard(image = "https://i.ytimg.com/vi/YiAe8EiFHOQ/maxresdefault.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://i.ytimg.com/vi/kkBcKF1sspg/maxresdefault.jpg", title = "PIRATAS - Magia, Casas, Evento y Mucho mas!")
//        HouseCard(image = "https://static.planetminecraft.com/files/resource_media/screenshot/1342/2013-10-19_155537_6566217.jpg", title = "Casa de Vegetta777")
//        HouseCard(image = "https://pbs.twimg.com/media/CsnqJCtWcAE85R1?format=jpg&name=4096x4096", title = "Planeta vegetta")
//        HouseCard(image = "https://i.ytimg.com/vi/Iu8a9LHBwRA/maxresdefault.jpg", title = "V DE VEGETTA: CASA ARBOL ACUATICA")
    }
}

@Composable
fun HouseCard(
    modifier: Modifier = Modifier,
    house: Houses
) {
    val expanded = remember { mutableStateOf(false) }
    Card(
        modifier.padding(bottom = spacing)
    ) {
        Box {
            Column {
                AsyncImage(
                    model = house.img,
                    contentDescription = null,
                    modifier = modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier
                        .animateContentSize()
                        .clickable { expanded.value = !expanded.value }
                        .padding(spacing / 2)
                ) {
                    Row(
                    ) {
                        Column(
                            modifier.weight(1f)
                        ) {
                            Text(text = house.title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                            Text(text = house.subtitle, style = MaterialTheme.typography.titleMedium)
                        }
                        IconButton(onClick = { expanded.value = !expanded.value }) {
                            Crossfade(targetState = expanded.value, label = "") {
                                if (it){
                                    Icon(Icons.Rounded.KeyboardArrowUp, contentDescription = null)
                                }else {
                                    Icon(Icons.Rounded.KeyboardArrowDown, contentDescription = null)
                                }
                            }
                        }
                    }
                    if (expanded.value){
                        Text(text = house.legal, style = MaterialTheme.typography.titleSmall)
                        Text(text = house.location, style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}