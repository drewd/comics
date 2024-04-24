package com.dobson.comics.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun DetailsRoute(
    viewModel: DetailsViewModel = hiltViewModel(),
    navigateBack: () -> Unit,
    navigateToDetails: (Int) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    // TODO Need screens for loading and error states
    uiState.comic?.let {
        DetailsScreen(
            comic = it,
            onBackClick = navigateBack,
            onPreviousClick = uiState.previousComicId?.let { { navigateToDetails(it) } },
            onNextClick = uiState.nextComicId?.let { { navigateToDetails(it) } },
        )
    }
}