package com.miiky.houser.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miiky.houser.R
import com.miiky.houser.ui.space_top
import com.miiky.houser.ui.spacing
import kotlinx.coroutines.delay

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val icon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    LaunchedEffect(key1 = true){
        delay(2000)
        navHost.navigate("login")
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        LottieAnimation(composition = icon, iterations = LottieConstants.IterateForever, modifier = modifier
            .padding(horizontal = space_top)
            .offset(y = -(space_top * 2)))
        Text(text = "Cargando...")
    }

}