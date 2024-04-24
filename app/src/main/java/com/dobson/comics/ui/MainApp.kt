package com.dobson.comics.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.dobson.comics.ui.details.DetailsRoute
import com.dobson.comics.ui.theme.ComicsTheme

@Composable
fun MainApp() {
    ComicsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val mainNavController = rememberMainNavController()
            NavHost(
                navController = mainNavController.navHostController,
                startDestination = "${MainDestinations.COMIC_DETAILS_ROUTE}/2",
            ) {
                mainNavGraph(
                    navigateToDetails = mainNavController::navigateToDetails,
                )
            }
        }
    }
}

private fun NavGraphBuilder.mainNavGraph(navigateToDetails: (Int, NavBackStackEntry) -> Unit) {
    composable(
        route = "${MainDestinations.COMIC_DETAILS_ROUTE}/{${MainDestinations.COMIC_ID_KEY}}",
        arguments = listOf(navArgument(MainDestinations.COMIC_ID_KEY) {
            type = NavType.IntType
            // TODO Don't love the default value - how do we startDestination with this
            defaultValue = 2
        })
    ) { from ->
        DetailsRoute(
            navigateBack = { },
            navigateToDetails = { navigateToDetails(it, from) }
        )
    }
}