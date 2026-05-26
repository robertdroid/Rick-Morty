package net.devrob.arkanotest.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import net.devrob.arkanotest.presentation.characterdetail.CharacterDetailScreen
import net.devrob.arkanotest.presentation.characters.CharactersScreen

@Composable
fun NavigationWrapper() {
    val backstack = rememberNavBackStack(Routes.Characters)

    NavDisplay(
        backStack = backstack,
        onBack = { backstack.removeLastOrNull() },
        entryDecorators =
            listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
        entryProvider = entryProvider {
            entry<Routes.Characters> {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        backstack.navigateTo(Routes.CharacterDetails(characterId))
                    }
                )
            }
            entry<Routes.CharacterDetails> { key ->
                CharacterDetailScreen(
                    characterId = key.id,
                    onNavigateBack = { backstack.navigateBack() }
                )
            }
        }
    )
}