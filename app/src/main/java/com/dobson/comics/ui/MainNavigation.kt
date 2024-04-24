package com.dobson.comics.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object MainDestinations {
    const val COMIC_DETAILS_ROUTE = "comic/details"

    const val COMIC_ID_KEY = "comicId"
}

@Composable
fun rememberMainNavController(
    navHostController: NavHostController = rememberNavController(),
): MainNavigation = remember(navHostController) {
    MainNavigation(navHostController)
}

@Stable
class MainNavigation(val navHostController: NavHostController) {
    fun navigateToDetails(comicId: Int, from: NavBackStackEntry) {
        if (from.lifecycle.currentState == Lifecycle.State.RESUMED) {
            navHostController.navigate(
                "${MainDestinations.COMIC_DETAILS_ROUTE}/$comicId",
            ) {
                popUpTo(
                    "${MainDestinations.COMIC_DETAILS_ROUTE}/{${MainDestinations.COMIC_ID_KEY}}"
                ) {
                    inclusive = true
                }
            }
        }
    }
}