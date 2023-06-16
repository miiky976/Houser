package com.miiky.houser.ui.screens.sesion

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.miiky.houser.ui.PasswordTexField
import com.miiky.houser.ui.spacing

@Composable
fun Password(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
    extra: String // para redireccionar a un lado o a otro y estableces titulos
){
    val password1 = remember { mutableStateOf("") }
    val password2 = remember { mutableStateOf("") }
    val password1_error = remember { mutableStateOf(0) }
    val password2_error = remember { mutableStateOf(0) }
    Column {
        PasswordTexField(pass = password1, error = password1_error, modifier = modifier.padding(bottom = spacing))
        PasswordTexField(pass = password2, error = password2_error, modifier = modifier.padding(bottom = spacing))
    }
}