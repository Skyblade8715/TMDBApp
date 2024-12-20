package com.skycom.tmdbapp.feature.movieList.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.skycom.tmdbapp.R
import com.skycom.tmdbapp.feature.movieList.model.UiMovie

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClear: () -> Unit,
    suggestions: List<UiMovie>,
    onSuggestionSelected: (UiMovie) -> Unit,
    onFocusChange: (Boolean) -> Unit
) {
    var focused by remember { mutableStateOf(false) }

    Box {
        TextField(value = query,
            onValueChange = { onQueryChange(it) },
            placeholder = { Text(stringResource(R.string.search_movies)) },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = {
                        onClear()
                        focused = false
                        onFocusChange(false)
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(R.string.clear_search)
                        )
                    }
                }
            },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onQueryChange(query)
                focused = false
                onFocusChange(false)
            }),
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { focusState ->
                    focused = focusState.isFocused
                    onFocusChange(focusState.isFocused)
                })

        if (focused && query.isNotEmpty() && suggestions.isNotEmpty()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp)
                    .zIndex(10f)
            ) {
                Card(
                    Modifier
                        .fillMaxWidth()
                        .heightIn(max = 200.dp),
                    elevation = CardDefaults.cardElevation(8.dp)
                ) {
                    LazyColumn {
                        items(suggestions) { movie ->
                            Text(text = movie.title, modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onSuggestionSelected(movie)
                                }
                                .padding(16.dp))
                        }
                    }
                }
            }
        }
    }
}
