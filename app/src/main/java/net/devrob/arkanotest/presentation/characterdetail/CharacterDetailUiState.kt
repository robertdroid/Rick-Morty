package net.devrob.arkanotest.presentation.characterdetail

import net.devrob.arkanotest.domain.model.CharacterDetail

sealed interface CharacterDetailUiState {
    data object Loading : CharacterDetailUiState
    data class Success(val character: CharacterDetail) : CharacterDetailUiState
    data class Error(val message: String) : CharacterDetailUiState
}