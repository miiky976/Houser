package com.miiky.houser.ui.screens.crap

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.FactCheck
import androidx.compose.material.icons.outlined.Speed
import androidx.compose.material.icons.outlined.SwapVerticalCircle
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.FactCheck
import androidx.compose.material.icons.rounded.Speed
import androidx.compose.material.icons.rounded.SwapVerticalCircle
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.R
import com.miiky.houser.ui.BottomNavItem

@Composable
fun CrapMaster(
    modifier: Modifier = Modifier,
) {
    val crapControl = rememberNavController()
    val backStackEntry = crapControl.currentBackStackEntryAsState()
    val items = listOf(
        BottomNavItem(
            stringResource(id = R.string.calculator),
            Icons.Outlined.Calculate,
            "calculator",
            Icons.Rounded.Calculate
        ),
        BottomNavItem(
            stringResource(id = R.string.todo),
            Icons.Outlined.FactCheck,
            "todo",
            Icons.Rounded.FactCheck
        ),
        BottomNavItem(
            stringResource(id = R.string.unit),
            Icons.Outlined.SwapVerticalCircle,
            "unit",
            Icons.Rounded.SwapVerticalCircle
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        NavHost(
            navController = crapControl,
            startDestination = "calculator",
            modifier = modifier.weight(1f)
        ) {
            composable("calculator") {
                Calculator()
            }
            composable("todo") {
                Todo()
            }
            composable("unit") {
                UnitConverse()
            }
        }
        NavigationBar(modifier = modifier.fillMaxWidth()) {
            items.forEach { item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                NavigationBarItem(
                    selected = selected,
                    onClick = { crapControl.navigate(item.route) },
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
}