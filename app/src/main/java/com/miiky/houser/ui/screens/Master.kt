package com.miiky.houser.ui.screens

import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.ExitToApp
import androidx.compose.material.icons.rounded.HomeRepairService
import androidx.compose.material.icons.rounded.HomeWork
import androidx.compose.material.icons.rounded.Settings
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.miiky.houser.R
import com.miiky.houser.data.direction
import com.miiky.houser.data.persistent.StoreSession
import com.miiky.houser.ui.BottomAnimatedItem
import com.miiky.houser.ui.BottomNavItem
import com.miiky.houser.ui.screens.crap.CrapMaster
import com.miiky.houser.ui.screens.features.MainMaster
import com.miiky.houser.ui.screens.settings.SettingsMaster
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
        BottomNavItem(
            stringResource(id = R.string.settings),
            Icons.Outlined.Settings,
            "settings",
            Icons.Rounded.Settings
        )
    )
    val anitems = listOf(
        BottomAnimatedItem(
            stringResource(id = R.string.home),
            LottieCompositionSpec.RawRes(R.raw.home),
            "main",
            remember { mutableStateOf(false) }
        ),
        BottomAnimatedItem(
            stringResource(id = R.string.crap),
            LottieCompositionSpec.RawRes(R.raw.explore),
            "crap",
            remember { mutableStateOf(false) }
        ),
        BottomAnimatedItem(
            stringResource(id = R.string.settings),
            LottieCompositionSpec.RawRes(R.raw.settings),
            "settings",
            remember { mutableStateOf(false) }
        )
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
                anitems.forEach {
                    val selected = it.route == backStackEntry.value?.destination?.route
                    val icon by rememberLottieComposition(spec = it.icon)
                    val shouldReverse = remember { mutableStateOf(false) }
                    val anim = rememberLottieAnimatable()
                    if (shouldReverse.value.not())
                        LaunchedEffect(icon) {
                            anim.animate(composition = icon, speed = 1f)
                            shouldReverse.value = true
                        }
                    if (shouldReverse.value) {
                        LaunchedEffect(icon) {
                            anim.animate(composition = icon, speed = -1f)
                            shouldReverse.value = false
                        }
                    }
                    NavigationDrawerItem(
                        label = { Text(text = it.title) },
                        selected = selected,
                        onClick = {
                            navControl.navigate(it.route)
                            scope.launch {
                                drawer.close()
                            }
                        },
                        icon = {
                            LottieAnimation(icon, { anim.value })
                        },
                        modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
//                items.forEach { item ->
//                    val selected = item.route == backStackEntry.value?.destination?.route
//                    NavigationDrawerItem(
//                        label = { Text(text = item.title) },
//                        selected = selected,
//                        onClick = {
//                            navControl.navigate(item.route)
//                            scope.launch { drawer.close() }
//                        },
//                        icon = {
//                            Crossfade(selected, label = "Justa") {
//                                if (!it) {
//                                    Icon(
//                                        imageVector = item.icon,
//                                        contentDescription = "${item.title} Icon",
//                                    )
//                                } else {
//                                    Icon(
//                                        imageVector = item.alticon,
//                                        contentDescription = "${item.title} Icon",
//                                    )
//                                }
//                            }
//                        },
//                        modifier = modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
//                    )
//                }
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
                composable("settings") {
                    SettingsMaster()
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
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val dataStore = StoreSession(context)
    val name = dataStore.getName.collectAsState(initial = "")
    val user = dataStore.getUser.collectAsState(initial = "")
    val last = dataStore.getLastname.collectAsState(initial = "")
    val image = dataStore.getImage.collectAsState(initial = "")
    val email = dataStore.getEmail.collectAsState(initial = "")
    Row {
        Card(
            modifier = modifier.size(128.dp)
        ) {
            Box(modifier = modifier.fillMaxSize()) {
                Image(
                    painterResource(id = R.drawable.chihuahua),
                    contentDescription = "Default profile picture",
                    modifier = modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
                AsyncImage(
                    "http://${direction.value}:3000/user/images/${image.value}",
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = modifier.fillMaxSize(),
                )
            }
        }
        Column(modifier = modifier.padding(spacing)) {
            Text(text = name.value+" "+last.value, style = MaterialTheme.typography.titleLarge)
            Text(
                text = user.value,
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