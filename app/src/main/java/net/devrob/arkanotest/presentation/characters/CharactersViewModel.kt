package net.devrob.arkanotest.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.time.debounce
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _loadedCharacters = MutableStateFlow<List<Character>>(emptyList())

    val charactersPagingData: Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope)

    @OptIn(FlowPreview::class)
    val searchState: StateFlow<CharactersSearchState> = combine(
        _searchQuery.debounce(300),
        _loadedCharacters
    ) { query, characters ->
        when {
            query.isBlank() -> CharactersSearchState.Inactive
            else -> {
                val filtered = characters.filter { character ->
                    character.name.contains(query, ignoreCase = true)
                }
                CharactersSearchState.Active(query = query, results = filtered)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), CharactersSearchState.Inactive)

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun clearSearch() {
        _searchQuery.value = ""
    }

    fun updateLoadedCharacters(characters: List<Character>) {
        _loadedCharacters.value = characters
    }

}