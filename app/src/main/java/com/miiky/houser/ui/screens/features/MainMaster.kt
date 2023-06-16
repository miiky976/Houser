package com.miiky.houser.ui.screens.features

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.Navigation
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.rounded.Face
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.R
import com.miiky.houser.ui.BottomNavItem
import com.miiky.houser.ui.space_bottom
import com.miiky.houser.ui.spacing

@Composable
fun MainMaster(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val navControl = rememberNavController()

    val items = listOf(
        BottomNavItem(
            stringResource(id = R.string.home),
            Icons.Outlined.Home,
            "home",
            Icons.Rounded.Home
        ),
        BottomNavItem(
            stringResource(id = R.string.notifications),
            Icons.Outlined.Notifications,
            "notifications",
            Icons.Rounded.NotificationsActive
        ),
        BottomNavItem(
            stringResource(id = R.string.account),
            Icons.Outlined.Face,
            "account",
            Icons.Rounded.Face
        )
    )

    val backStackEntry = navControl.currentBackStackEntryAsState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        NavHost(
            navController = navControl,
            startDestination = "home",
            modifier = modifier.weight(1f)
        ) {
            composable("home") {
                Home(navHost = navControl)
            }
            composable("notifications") {
                Notifications(navHost = navControl)
            }
            composable("account") {
                Account(navHost = navControl)
            }
        }
        NavigationBar(modifier = modifier.fillMaxWidth()) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { navControl.navigate(item.route) },
                    label = {
                        Text(
                            text = item.title,
                            fontWeight = FontWeight.SemiBold,
                        )
                    },
                    icon = {
                        Crossfade(selected, label = "Justa") {
                            if (!it) {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = "${item.title} Icon",
                                )
                            } else {
                                Icon(
                                    imageVector = item.alticon,
                                    contentDescription = "${item.title} Icon",
                                )
                            }
                        }

                    },
                    alwaysShowLabel = false
                )
            }
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = space_bottom * 2, end = spacing),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom
    ) {
        FloatingActionButton(onClick = { /*TODO*/ }, modifier = modifier) {
            Icon(Icons.Default.MyLocation, contentDescription = null)
        }
        Spacer(modifier = Modifier.height(8.dp))
        ExtendedFloatingActionButton(onClick = { /*TODO*/ }, modifier = modifier) {
            Icon(Icons.Default.Navigation, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "FAB Extended")
        }
    }
}