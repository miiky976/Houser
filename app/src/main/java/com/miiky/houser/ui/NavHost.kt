package com.miiky.houser.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.ui.screens.session.Code
import com.miiky.houser.ui.screens.Loading
import com.miiky.houser.ui.screens.session.Login
import com.miiky.houser.ui.screens.Master
import com.miiky.houser.ui.screens.Offline
import com.miiky.houser.ui.screens.session.Password
import com.miiky.houser.ui.screens.session.Signup
import com.miiky.houser.ui.screens.settings.SettingsMaster

@Composable
fun NavigationMain(
) {
    val navControll = rememberNavController()
    NavHost(
        navController = navControll,
        startDestination = "loading",
    ){
        composable("loading"){
            Loading(navHost = navControll)
        }
        composable("login"){
            Login(navHost = navControll)
        }
        composable("signup"){
            Signup(navHost = navControll)
        }
        composable("code"){
            Code(navHost = navControll)
        }
        composable("master"){
            Master(navHost = navControll)
        }
        composable("offline"){
            Offline(navHost = navControll)
        }
        composable("setpassword"){
            Password(navHost = navControll, extra = "Signup")
        }
        composable("changepassword"){
            Password(navHost = navControll, extra = "Change")
        }
        // remover XD
        composable("direccion"){
            SettingsMaster(navHost = navControll)
        }
    }
}