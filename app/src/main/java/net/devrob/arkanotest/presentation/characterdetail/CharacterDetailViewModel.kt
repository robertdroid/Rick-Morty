package net.devrob.arkanotest.presentation.characterdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.devrob.arkanotest.domain.usecase.GetCharacterDetailUseCase
import javax.inject.Inject

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getCharacterDetailUseCase: GetCharacterDetailUseCase
) : ViewModel() {
    private var characterId: Int = -1
    private val _uiState = MutableStateFlow<CharacterDetailUiState>(CharacterDetailUiState.Loading)
    val uiState: StateFlow<CharacterDetailUiState> = _uiState.asStateFlow()

    fun setCharacterId(id: Int) {
        characterId = id
        loadCharacterDetail()
    }

    fun loadCharacterDetail() {
        if (characterId < 0) return
        viewModelScope.launch {
            _uiState.value = CharacterDetailUiState.Loading
            try {
                val character = getCharacterDetailUseCase(characterId)
                _uiState.value = CharacterDetailUiState.Success(character)
            } catch (e: Exception) {
                _uiState.value = CharacterDetailUiState.Error(
                    e.localizedMessage ?: "Failed to load character details"
                )
            }
        }
    }
}