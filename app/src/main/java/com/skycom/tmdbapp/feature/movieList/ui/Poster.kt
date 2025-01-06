package com.skycom.tmdbapp.feature.movieList.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.skycom.tmdbapp.R
import com.skycom.tmdbapp.feature.movieList.model.UiMovie


@Composable
fun Poster(
    onMovieClick: (Int) -> Unit,
    onLikeClicked: (Int, Boolean) -> Unit,
    movie: UiMovie,
    modifier: Modifier = Modifier,
) {
    val isLiked = remember { mutableStateOf(movie.isLiked) }
    Box(modifier = modifier
        .fillMaxWidth()
        // Poster image is 500x750 = 0.667 aspect Ratio
        .aspectRatio(.667f)
        .clickable {
            onMovieClick(movie.id)
        }) {
        val starIcon = if (isLiked.value) Icons.Filled.Star else Icons.Outlined.Star
        val starTint = if (isLiked.value) Color.Yellow else Color.Gray

        Image(
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .fillMaxSize(),
            painter = rememberAsyncImagePainter(
                model = movie.formattedPosterUrl,
                error = painterResource(id = R.drawable.ic_broken_link),
            ),
            contentDescription = stringResource(R.string.poster, movie.title),
        )
        Box(
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.TopEnd)
                .background(
                    MaterialTheme.colorScheme.background, MaterialTheme.shapes.large
                )
                .padding(8.dp)
        ) {
            Icon(imageVector = starIcon,
                contentDescription = stringResource(R.string.toggle_like),
                tint = starTint,
                modifier = Modifier
                    .size(56.dp)
                    .clickable {
                        onLikeClicked(movie.id, !movie.isLiked)
                        isLiked.value = !isLiked.value
                    })
        }
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
                .align(Alignment.BottomEnd)
                .background(
                    MaterialTheme.colorScheme.background, MaterialTheme.shapes.large
                )
                .padding(horizontal = 8.dp)
        ) {
            Text(
                textAlign = TextAlign.Center,
                text = movie.audienceScore,
                style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
            )
        }
    }
}