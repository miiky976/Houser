package com.miiky.houser

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import com.miiky.houser.ui.NavigationMain
import com.miiky.houser.ui.screens.sesion.Login
import com.miiky.houser.ui.theme.HouserTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val tema = remember { mutableStateOf(false) }
            HouserTheme (dynamicColor = tema.value) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationMain(tema)
                }
            }
        }
    }
}

@Composable
fun AppContent(modifier: Modifier = Modifier){
    Login()
}

@Preview(showBackground = true, )
@Composable
fun GreetingPreview() {
    HouserTheme {
        Login()
    }
}