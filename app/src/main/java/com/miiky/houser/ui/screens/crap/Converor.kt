package com.miiky.houser.ui.screens.crap

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DesignServices
import androidx.compose.material.icons.filled.Straighten
import androidx.compose.material.icons.filled.Thermostat
import androidx.compose.material.icons.filled.ViewInAr
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DesignServices
import androidx.compose.material.icons.outlined.HorizontalRule
import androidx.compose.material.icons.outlined.MonitorWeight
import androidx.compose.material.icons.outlined.Sick
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material.icons.outlined.Thermostat
import androidx.compose.material.icons.outlined.ViewInAr
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material.icons.rounded.AccessTimeFilled
import androidx.compose.material.icons.rounded.MonitorWeight
import androidx.compose.material.icons.rounded.Sick
import androidx.compose.material.icons.rounded.Thermostat
import androidx.compose.material.icons.rounded.WaterDrop
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.ui.BottomNavItem
import com.miiky.houser.ui.screens.crap.converor.Length
import com.miiky.houser.ui.screens.crap.converor.Temperature
import com.miiky.houser.ui.screens.crap.converor.Volume
import com.miiky.houser.ui.screens.crap.converor.Weight
import com.miiky.houser.ui.spacing

@Composable
fun UnitConverse(
    modifier: Modifier = Modifier,
) {
    val state = remember { mutableStateOf(0) }

    val navControl = rememberNavController()
    val backStackEntry = navControl.currentBackStackEntryAsState()

    val operations = listOf(
        BottomNavItem("Temperatura", Icons.Outlined.Sick, "temperature", Icons.Rounded.Sick),
        BottomNavItem("Peso", Icons.Outlined.MonitorWeight, "weight", Icons.Rounded.MonitorWeight),
        BottomNavItem("Longitud", Icons.Outlined.DesignServices, "length", Icons.Filled.DesignServices),
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TabRow(selectedTabIndex = state.value) {
            operations.forEachIndexed{index, item ->
                val selected = item.route == backStackEntry.value?.destination?.route
                Tab(
                    selected = selected,
                    onClick = {
                              state.value = index
                        navControl.navigate(item.route)
                    },
                    text = { Text(text = item.title)},
                    icon = {
                        Crossfade(selected, label = "Justa") {
                            if (!it){
                                Icon(item.icon, contentDescription = null)
                            }else {
                                Icon(item.alticon, contentDescription = null)
                            }
                        }
                    }
                )

            }
        }
        NavHost(navController = navControl, startDestination = "temperature", modifier = modifier.weight(1f)){
            composable("temperature"){
                Temperature()
            }
            composable("weight"){
                Weight()
            }
            composable("volume"){
                Volume()
            }
            composable("length"){
                Length()
            }
        }
    }
}