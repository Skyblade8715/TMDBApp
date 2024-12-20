package com.skycom.tmdbapp.feature.movieList.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.skycom.tmdbapp.feature.movieList.model.UiMovie

@Composable
fun MoviesList(
    movies: LazyPagingItems<UiMovie>,
    searchedMovies: List<UiMovie>?,
    onMovieClicked: (Int) -> Unit,
    onMovieLiked: (Int, Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        items(searchedMovies?.size ?: movies.itemCount) { index ->
            val movie = searchedMovies?.get(index) ?: movies[index]
            movie?.let {
                Column {
                    MovieItem(
                        movie = it,
                        onMovieClicked = onMovieClicked,
                        onLikeClicked = onMovieLiked,
                    )
                }
            }
        }
    }
}
