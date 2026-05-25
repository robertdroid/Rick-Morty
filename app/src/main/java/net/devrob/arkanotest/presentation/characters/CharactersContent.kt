package net.devrob.arkanotest.presentation.characters

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.presentation.components.CharacterItem
import net.devrob.arkanotest.presentation.components.EmptySearchState
import net.devrob.arkanotest.presentation.components.ErrorState
import net.devrob.arkanotest.presentation.components.LoadingState

@Composable
fun CharactersContent(
    characters: LazyPagingItems<Character>,
    searchState: CharactersSearchState,
    onLoadedCharacterChanged: (List<Character>) -> Unit,
    modifier: Modifier = Modifier
) {
    val refreshState = characters.loadState.refresh
    val isRefreshing = refreshState is LoadState.Loading

    LaunchedEffect(characters) {
        snapshotFlow { characters.itemSnapshotList.items }
            .collect { items ->
                onLoadedCharacterChanged(items)
            }
    }

    when {
        refreshState is LoadState.Loading && characters.itemCount == 0 -> {
            LoadingState(modifier = modifier)
        }
        refreshState is LoadState.Error && characters.itemCount == 0 -> {
            val error = refreshState.error
            ErrorState(
                message = error.localizedMessage ?: "An unexpected error occurred",
                onRetry = { characters.retry() },
                modifier = modifier
            )
        }
        searchState is CharactersSearchState.Active && searchState.isEmpty -> {
            EmptySearchState(
                query = searchState.query,
                modifier = modifier
            )
        }
        searchState is CharactersSearchState.Active -> {
            FilteredCharacterList(
                characters = searchState.results,
                modifier = modifier
            )
        }
        else -> {
            PullToRefreshBox(
                isRefreshing = isRefreshing,
                onRefresh = { characters.refresh() },
                modifier = modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        count = characters.itemCount,
                        key = { index -> characters[index]?.id ?: index }
                    ) { index ->
                        characters[index]?.let { character ->
                            CharacterItem(character = character)
                        }
                    }

                    when (val appendState = characters.loadState.append) {
                        is LoadState.Loading -> {
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    CircularProgressIndicator()
                                }
                            }
                        }
                        is LoadState.Error -> {
                            item {
                                ErrorState(
                                    message = appendState.error.localizedMessage
                                        ?: "Failed to load more characters",
                                    onRetry = { characters.retry() },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                        is LoadState.NotLoading -> Unit
                    }
                }
            }
        }
    }
}

@Composable
private fun FilteredCharacterList(
    characters: List<Character>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = characters,
            key = { it.id }
        ) { character ->
            CharacterItem(character = character)
        }
    }
}