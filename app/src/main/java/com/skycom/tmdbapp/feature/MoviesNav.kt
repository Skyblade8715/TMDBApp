package com.skycom.tmdbapp.feature

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.skycom.tmdbapp.feature.movieDetails.ui.MovieDetailsScreen
import com.skycom.tmdbapp.feature.movieList.ui.MovieListScreen


@Composable
fun MoviesNav(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "movie_list",
    ) {
        composable("movie_list") {

            MovieListScreen(
                onMovieClick = { movieId -> navController.navigate("movie_details/$movieId") },
            )
        }

        composable(
            route = "movie_details/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType }),
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId")
            movieId?.let {
                MovieDetailsScreen(
                    movieId = it,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}