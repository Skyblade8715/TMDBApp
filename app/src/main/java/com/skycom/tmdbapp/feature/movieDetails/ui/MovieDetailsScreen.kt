package com.skycom.tmdbapp.feature.movieDetails.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.skycom.tmdbapp.R
import com.skycom.tmdbapp.core.common.model.UiState
import com.skycom.tmdbapp.core.common.ui.ErrorScreen
import com.skycom.tmdbapp.core.common.ui.LoadingScreen
import com.skycom.tmdbapp.feature.movieDetails.model.UiMovieDetails


@Composable
fun MovieDetailsScreen(
    movieId: Int,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    LaunchedEffect(movieId) {
        viewModel.fetchMovieDetails(movieId)
    }
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState) {
        UiState.Loading -> LoadingScreen()

        is UiState.Success -> {
            MovieDetailsScreenInternal(
                movie = (uiState as UiState.Success<UiMovieDetails>).data,
                onLikeClicked = viewModel::toggleLiked,
                onBack = onBack,
                modifier = modifier,
            )
        }

        is UiState.Error -> ErrorScreen(
            message = (uiState as UiState.Error).exception.message
                ?: stringResource(R.string.an_error_occurred),
            onRetry = { viewModel.fetchMovieDetails(movieId) }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsScreenInternal(
    movie: UiMovieDetails,
    onBack: () -> Unit,
    onLikeClicked: () -> Unit,
    modifier: Modifier = Modifier
) {

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(movie.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = stringResource(
                                R.string.back
                            )
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(16f / 9f),
                contentAlignment = Alignment.BottomStart
            ) {
                AsyncImage(
                    model = movie.formattedBackdropUrl,
                    contentDescription = stringResource(R.string.backdrop, movie.title),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .align(Alignment.BottomStart),
                    color = Color.White.copy(alpha = 0.5f)
                ) {
                    Text(
                        text = movie.title,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.audience_score, movie.audienceScore),
                )
                Text(
                    text = movie.formattedReleaseDate,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = movie.overview,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(24.dp))

                val starIcon = if (movie.isLiked) Icons.Filled.Star else Icons.Outlined.Star
                val starTint = if (movie.isLiked) Color.Yellow else Color.Gray

                Icon(
                    imageVector = starIcon,
                    contentDescription = stringResource(R.string.toggle_like),
                    tint = starTint,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(64.dp)
                        .clickable { onLikeClicked() }
                )
            }
        }
    }
}
