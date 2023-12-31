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
import androidx.fragment.app.FragmentActivity
import com.miiky.houser.data.Themed
import com.miiky.houser.ui.NavigationMain
import com.miiky.houser.ui.screens.session.Login
import com.miiky.houser.ui.theme.HouserTheme

class MainActivity : FragmentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            HouserTheme (dynamicColor = Themed.value, darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationMain()
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