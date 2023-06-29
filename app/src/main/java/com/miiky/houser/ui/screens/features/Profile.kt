package com.miiky.houser.ui.screens.features

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.ui.spacing
import kotlinx.coroutines.launch

@Composable
fun Account(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current)
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)

    val activity = (context as? ComponentActivity)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = spacing)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(text = "Account")
        Button(onClick = {
            scope.launch {
                dataStore.endSession()
                activity?.let {
                    val intent = Intent(context, it.javaClass)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    it.finishAffinity()
                }
            }
        }) {
            Text(text = "Terminar sesion")
        }
    }
}