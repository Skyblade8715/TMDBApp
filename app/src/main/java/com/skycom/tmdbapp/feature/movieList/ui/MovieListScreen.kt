package com.skycom.tmdbapp.feature.movieList.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.skycom.tmdbapp.feature.movieList.model.UiMovie

@Composable
fun MovieListScreen(
    modifier: Modifier = Modifier,
    onMovieClick: (Int) -> Unit,
) {
    val viewModel = hiltViewModel<MoviesViewModel>()

    val uiState by viewModel.uiState.collectAsState()
    val movies: LazyPagingItems<UiMovie> = viewModel.movies.collectAsLazyPagingItems()

    var focused by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            SearchBar(query = uiState.searchQuery,
                onQueryChange = { viewModel.updateSearchQuery(it) },
                onClear = {
                    viewModel.clearSearch()
                    focused = false
                },
                suggestions = if (focused && uiState.searchQuery.isNotEmpty()) uiState.autocompleteSuggestions else emptyList(),
                onSuggestionSelected = {
                    viewModel.updateSearchQuery(it.title)
                    focused = false
                },
                onFocusChange = {
                    focused = it
                })

            MoviesList(
                movies = movies,
                searchedMovies = if (uiState.searchQuery.isNotEmpty()) uiState.autocompleteSuggestions else null,
                modifier = Modifier.padding(top = 16.dp),
                onMovieClicked = onMovieClick,
                onMovieLiked = viewModel::toggleLiked,
            )
        }
    }
}
