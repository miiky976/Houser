package com.miiky.houser.ui

import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieCompositionResult
import com.airbnb.lottie.compose.LottieCompositionSpec

data class BottomNavItem(
    val title: String,
    val icon: ImageVector,
    val route: String,
    val alticon: ImageVector,
)

data class BottomAnimatedItem(
    val title: String,
    val icon: LottieCompositionSpec,
    val route: String,
    val play: MutableState<Boolean>
)