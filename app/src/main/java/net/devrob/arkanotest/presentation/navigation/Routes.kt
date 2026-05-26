package net.devrob.arkanotest.presentation.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed class Routes: NavKey {

    @Serializable data object Characters: Routes()

    @Serializable data class CharacterDetails(val id: Int) : Routes()
}