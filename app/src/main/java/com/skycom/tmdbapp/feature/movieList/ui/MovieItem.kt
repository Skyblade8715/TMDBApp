package com.skycom.tmdbapp.feature.movieList.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.skycom.tmdbapp.feature.movieList.model.UiMovie

@Composable
fun MovieItem(
    movie: UiMovie,
    onMovieClicked: (Int) -> Unit,
    onLikeClicked: (Int, Boolean) -> Unit,
) {
    Poster(
        onMovieClick = onMovieClicked,
        onLikeClicked = onLikeClicked,
        movie = movie,
    )
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            modifier = Modifier
                .padding(start = 8.dp, top = 12.dp)
                .fillMaxWidth(0.6f),
            style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
            text = movie.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.headlineSmall,
            text = movie.formattedReleaseDate,
            maxLines = 1,
        )
    }
}