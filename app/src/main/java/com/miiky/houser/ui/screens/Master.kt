package com.miiky.houser.ui.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.HomeRepairService
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.HomeRepairService
import androidx.compose.material.icons.rounded.HomeWork
import androidx.compose.material3.Card
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.miiky.houser.R
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.ui.BottomNavItem
import com.miiky.houser.ui.screens.crap.CrapMaster
import com.miiky.houser.ui.screens.features.MainMaster
import com.miiky.houser.ui.screens.user.UserMaster
import com.miiky.houser.ui.spacing
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Master(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val drawer = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val itemss = listOf(Icons.Default.Favorite, Icons.Default.Face, Icons.Default.Email)
    val selectedItems = remember { mutableStateOf(itemss[0]) }

    val navControl = rememberNavController()

    val backStackEntry = navControl.currentBackStackEntryAsState()

    val items = listOf(
        BottomNavItem(
            stringResource(id = R.string.main),
            Icons.Outlined.HomeWork,
            "main",
            Icons.Rounded.HomeWork
        ),
        BottomNavItem(
            stringResource(id = R.string.crap),
            Icons.Outlined.HomeRepairService,
            "crap",
            Icons.Rounded.HomeRepairService
        ),
    )
    ModalNavigationDrawer(
        drawerState = drawer,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = modifier.height(spacing))
                Text(
                    text = "Houser",
                    modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    style = MaterialTheme.typography.displaySmall
                )
                Card(
                    modifier = modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                        .padding(bottom = spacing)
                        .fillMaxWidth()
                ) {
                    NavHeader(modifier = modifier
                        .clickable {
                            navControl.navigate("user")
                            scope.launch { drawer.close() }
                        })
                }
                items.forEach { item ->
                    val selected = item.route == backStackEntry.value?.destination?.route
                    NavigationDrawerItem(
                        label = { Text(text = item.title) },
                        selected = selected,
                        onClick = {
                            navControl.navigate(item.route)
                            scope.launch { drawer.close() }
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
                        modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
                Spacer(
                    modifier = modifier
                        .weight(1f)
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
                NavFooter(
                    modifier = modifier
                        .padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        },
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            TopAppBar(
                title = { Text(text = stringResource(id = R.string.app_name)) },
                navigationIcon = {
                    IconButton(onClick = { scope.launch { drawer.open() } }) {
                        Icon(Icons.Default.Menu, contentDescription = null)
                    }
                }
            )
            NavHost(
                navController = navControl,
                startDestination = "main",
                modifier = modifier.weight(1f)
            ) {
                composable("main") {
                    MainMaster()
                }
                composable("crap") {
                    CrapMaster()
                }
                composable("user") {
                    UserMaster()
                }
            }
        }
    }
}

@Composable
fun NavHeader(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    Row {
        Card(
            modifier = modifier.size(128.dp)
        ) {
            Image(
                painterResource(id = R.drawable.chihuahua),
                contentDescription = "Default profile picture",
                modifier = modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Column(modifier = modifier.padding(spacing)) {
            Text(text = "Nombre de usuario", style = MaterialTheme.typography.titleLarge)
            Text(
                text = "nickname",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavFooter(
    modifier: Modifier = Modifier,
    navHost: NavHostController = NavHostController(LocalContext.current),
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)
    val activity = (context as? ComponentActivity)
    NavigationDrawerItem(
        label = {
            Text(text = stringResource(id = R.string.exit))
        },
        selected = false,
        onClick = {
            scope.launch {
                dataStore.endSession()
                activity?.let {
                    val intent = Intent(context, it.javaClass)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    context.startActivity(intent)
                    it.finishAffinity()
                }
            }
        },
        icon = {
            Icon(Icons.Rounded.ExitToApp, contentDescription = null)
        }
    )
}


@Preview
@Composable
fun PreviewMaster() {
    Master()
}