package com.miiky.houser.ui.screens.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miiky.houser.R
import com.miiky.houser.ui.spacing

@Composable
fun Home(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        val icon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.home))
        val play = remember { mutableStateOf(false) }
        val speed = remember { mutableStateOf(1f) }
        val prog by animateLottieCompositionAsState(composition = icon, isPlaying = play.value, speed = speed.value, restartOnPlay = true)
        Text(text = "Home")
        IconButton(onClick = {
            if (prog == 1f || prog == 0f){
                play.value = !play.value
            }
        }) {
            LottieAnimation(composition = icon, progress = {prog})
            LaunchedEffect(prog){
                if (play.value && prog == 1f){
                    speed.value = -1f
                }
                if (play.value && prog == 0f){
                    speed.value = 1f
                    play.value = false
                }
            }
        }
    }
}