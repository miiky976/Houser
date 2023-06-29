package com.miiky.houser.data

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData

var direction = "192.168.0.8"
val mDirection = mutableStateOf("")

val MyTheme = MutableLiveData<Boolean>(false)
val Themed = mutableStateOf(false)