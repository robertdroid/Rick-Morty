package net.devrob.arkanotest.presentation.characters

import net.devrob.arkanotest.domain.model.Character

sealed interface CharactersSearchState {
    data object Inactive : CharactersSearchState

    data class Active(
        val query: String,
        val results: List<Character>
    ) : CharactersSearchState {
        val isEmpty: Boolean get() = results.isEmpty()
    }
}