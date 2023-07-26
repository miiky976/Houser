package com.miiky.houser.ui.screens.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.R
import com.miiky.houser.ui.screens.use.UserEdit
import com.miiky.houser.ui.spacing

@Composable
fun UserMaster(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    Column(
        modifier = modifier
            .padding(spacing)
            .fillMaxSize()
    ) {
        UserNavigation()
    }
}

@Composable
fun UserNavigation() {
    val usernav = rememberNavController()
    NavHost(navController = usernav, startDestination = "edit"){
        composable("edit"){
            UserEdit(navHost = usernav)
        }
        composable("main"){
            UserMain(navHost = usernav)
        }
    }
}