@file:OptIn(ExperimentalMaterial3Api::class)

package com.miiky.houser.ui

import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.rounded.Email
import androidx.compose.material.icons.rounded.Key
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miiky.houser.R
import java.security.MessageDigest

val spacing = 16.dp
val space_top = 64.dp
val space_bottom = 64.dp
val anim = -20

var direccion = "192.168.0.8"



@Composable
fun AnimatedAppIcon(modifier: Modifier = Modifier){
    val icon by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.home))
    LottieAnimation(composition = icon, iterations = LottieConstants.IterateForever, modifier = modifier
        .padding(horizontal = space_top)
        .offset(y = -(space_top * 2)))
}

fun hashString(input: String): String {
    return MessageDigest
        .getInstance("SHA-256")
        .digest(input.toByteArray())
        .fold("") { str, it -> str + "%02x".format(it) }
}

@Composable
fun EmailTextField(
    modifier: Modifier = Modifier,
    email: MutableState<String>,
    error: MutableState<Int>
) {
    fun validate(email: String) {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            error.value = 1
        }else {
            error.value = 0
        }
    }
    OutlinedTextField(
        value = email.value,
        label = {
            Text(text = stringResource(id = R.string.email))
        },
        onValueChange = {
            email.value = it
            validate(email.value)
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        keyboardActions = KeyboardActions { validate(email.value) },
        supportingText = {
            AnimatedVisibility(visible = error.value==1,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
                ) {
                Text(
                    text = stringResource(id = R.string.emailnotvaliid),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            AnimatedVisibility(visible = error.value==2,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
            ) {
                Text(
                    text = stringResource(id = R.string.empty),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        isError = error.value != 0,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = spacing),
        singleLine = true,
        leadingIcon = {
            Icon(Icons.Rounded.Email, contentDescription = null)
        }
    )
}

@Composable
fun PasswordTexField(
    modifier: Modifier = Modifier,
    pass: MutableState<String>,
    error: MutableState<Int>
){
//    val pass_pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")
//    fun validate(pass: String){
//        if (!pass_pattern.matcher(pass).matches()){
//            error.value = 1
//        }
//    }
    val hidden = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = pass.value,
        onValueChange ={
            pass.value=it
            error.value = 0
        },
        label = { Text(text = stringResource(id = R.string.password))},
        modifier = modifier
            .fillMaxWidth(),
        isError = error.value != 0,
        singleLine = true,
        supportingText = {
            AnimatedVisibility(visible = error.value == 1,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
            ) {
                Text(
                    text = stringResource(id = R.string.empty),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            AnimatedVisibility(visible = error.value == 2,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
            ) {
                Text(
                    text = stringResource(id = R.string.password_missed),
                    color = MaterialTheme.colorScheme.error,
                )
            }
            AnimatedVisibility(visible = error.value == 3,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
            ) {
                Text(
                    text = stringResource(id = R.string.passwordnotmatch),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions {
            error.value = 0
        },
        visualTransformation = if (hidden.value) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            IconButton(onClick = {hidden.value = !hidden.value}) {
                // import animations XD
                // import icons.extra
                Crossfade(targetState = hidden.value, label = "IRONOU") {
                    if (it) {
                        Icon(Icons.Default.VisibilityOff, contentDescription = null)
                    }else {
                        Icon(Icons.Filled.Visibility, contentDescription = null)
                    }
                }
            }

        },
        leadingIcon = {
            Icon(Icons.Rounded.Key, contentDescription = null)
        }
    )
}

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    value: MutableState<String>,
    error: MutableState<Int>,
    label: String,
    icon: ImageVector = Icons.Default.Edit
){
    OutlinedTextField(
        value = value.value,
        onValueChange ={
            value.value=it
            error.value = 0
        },
        label = { Text(text = label)},
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = spacing),
        isError = error.value != 0,
        singleLine = true,
        supportingText = {
            AnimatedVisibility(visible = error.value == 1,
                enter = slideInVertically { anim },
                exit = slideOutVertically { anim }
            ) {
                Text(
                    text = stringResource(id = R.string.empty),
                    color = MaterialTheme.colorScheme.error,
                )
            }
        },
        keyboardActions = KeyboardActions { error.value = 0 },
        leadingIcon = {
            Icon(icon, contentDescription = null)
        }
    )
}