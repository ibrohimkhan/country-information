package com.kodeco.android.countryinfo.ui

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.EaseOut
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.kodeco.android.countryinfo.networking.buildApiService
import com.kodeco.android.countryinfo.repository.CountryRepository
import com.kodeco.android.countryinfo.repository.CountryRepositoryImpl
import com.kodeco.android.countryinfo.ui.screens.Screens
import com.kodeco.android.countryinfo.ui.screens.about.AboutScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsScreen
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModel
import com.kodeco.android.countryinfo.ui.screens.countrydetails.CountryDetailsViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoScreen
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModel
import com.kodeco.android.countryinfo.ui.screens.countryinfo.CountryInfoViewModelFactory
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoIntent
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoScreen
import com.kodeco.android.countryinfo.ui.screens.tapinfo.TapInfoViewModel
import com.kodeco.android.countryinfo.ui.theme.MyApplicationTheme


const val COUNTRY_KEY = "countryName"

@Composable
fun ApplicationNavigation(repository: CountryRepository) {

    val navController = rememberNavController()

    val countryInfoViewModel: CountryInfoViewModel = viewModel(
        factory = CountryInfoViewModelFactory(repository)
    )

    val viewModel: CountryDetailsViewModel = viewModel(
        factory = CountryDetailsViewModelFactory(repository)
    )

    val tapInfoViewModel: TapInfoViewModel = viewModel()

    var bottomBarVisibility by rememberSaveable { mutableStateOf(true) }
    val items = listOf(Screens.CountryList, Screens.TapInfo)

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = bottomBarVisibility,
            ) {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {
                                Icon(
                                    imageVector = screen.icon,
                                    contentDescription = screen.name
                                )
                            },
                            label = {
                                Text(text = screen.name)
                            },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.path } == true,
                            onClick = {
                                navController.navigate(screen.path) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }

                                    launchSingleTop = true
                                    restoreState = true
                                }
                            },
                        )
                    }
                }
            }
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = "countries",
            enterTransition = { EnterTransition.None },
            exitTransition = { ExitTransition.None },
            modifier = Modifier.padding(it)
        ) {
            composable(route = Screens.CountryList.path) {
                LaunchedEffect(null) {
                    bottomBarVisibility = true
                }

                CountryInfoScreen(
                    countryInfoViewModel = countryInfoViewModel,
                    onCountryClicked = {
                        tapInfoViewModel.processIntent(TapInfoIntent.Tap)
                        navController.navigate("countryDetails/$it")
                    },
                    navigateToAboutScreen = { navController.navigate(Screens.About.path) }
                )
            }

            composable(
                route = "countryDetails/{$COUNTRY_KEY}",
                arguments = listOf(
                    navArgument(COUNTRY_KEY) {
                        type = NavType.StringType
                    },
                ),
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(300, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Up
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(300, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Down
                    )
                }
            ) {
                LaunchedEffect(null) {
                    bottomBarVisibility = false
                }

                val country = it.arguments!!.getString(COUNTRY_KEY)!!

                CountryDetailsScreen(
                    countryName = country,
                    countryDetailsViewModel = viewModel,
                    onBackClicked = {
                        tapInfoViewModel.processIntent(TapInfoIntent.TapBack)
                        navController.navigateUp()
                    },
                )
            }

            composable(route = Screens.TapInfo.path) {
                LaunchedEffect(null) {
                    bottomBarVisibility = true
                }

                TapInfoScreen(
                    viewModel = tapInfoViewModel
                )
            }

            composable(
                route = Screens.About.path,
                enterTransition = {
                    slideIntoContainer(
                        animationSpec = tween(500, easing = EaseIn),
                        towards = AnimatedContentTransitionScope.SlideDirection.Down
                    )
                },
                exitTransition = {
                    slideOutOfContainer(
                        animationSpec = tween(400, easing = EaseOut),
                        towards = AnimatedContentTransitionScope.SlideDirection.Left
                    )
                }
            ) {
                LaunchedEffect(null) {
                    bottomBarVisibility = false
                }

                AboutScreen {
                    tapInfoViewModel.processIntent(TapInfoIntent.TapBack)
                    navController.navigateUp()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ApplicationNavigationPreview() {
    val repository = CountryRepositoryImpl(buildApiService())

    MyApplicationTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {
            ApplicationNavigation(repository)
        }
    }
}
