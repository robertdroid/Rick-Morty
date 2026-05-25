package net.devrob.arkanotest.presentation.characters

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.flowOf
import net.devrob.arkanotest.ui.theme.ArkanoTestTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.testutil.CharacterTestFactory

@RunWith(AndroidJUnit4::class)
class CharactersScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun loadingState_displaysProgressIndicator() {
        val loadingPagingData = PagingData.empty<Character>(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Loading,
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )

        composeTestRule.setContent {
            ArkanoTestTheme {
                val characters = flowOf(loadingPagingData).collectAsLazyPagingItems()
                CharactersContent(
                    characters = characters,
                    searchState = CharactersSearchState.Active("", emptyList()),
                    onLoadedCharacterChanged = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Loading content")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysErrorMessageAndRetryButton() {
        val errorMessage = "Network error occurred"
        val errorPagingData = PagingData.empty<Character>(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Error(Exception(errorMessage)),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )

        composeTestRule.setContent {
            ArkanoTestTheme {
                val characters = flowOf(errorPagingData).collectAsLazyPagingItems()
                CharactersContent(
                    characters = characters,
                    searchState = CharactersSearchState.Active("", emptyList()),
                    onLoadedCharacterChanged = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
    }

    @Test
    fun retryButton_isClickable() {
        val errorMessage = "Network error occurred"
        val errorPagingData = PagingData.empty<Character>(
            sourceLoadStates = LoadStates(
                refresh = LoadState.Error(Exception(errorMessage)),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = true)
            )
        )

        composeTestRule.setContent {
            ArkanoTestTheme {
                val characters = flowOf(errorPagingData).collectAsLazyPagingItems()
                CharactersContent(
                    characters = characters,
                    searchState = CharactersSearchState.Active("", emptyList()),
                    onLoadedCharacterChanged = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Retry")
            .performClick()
    }

    @Test
    fun characterList_rendersItemsCorrectly() {
        val characters = listOf(
            CharacterTestFactory.createAliveCharacter(),
            CharacterTestFactory.createDeadCharacter(),
            CharacterTestFactory.createUnknownStatusCharacter()
        )

        val successPagingData = PagingData.from(
            data = characters,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = false)
            )
        )

        composeTestRule.setContent {
            ArkanoTestTheme {
                val pagingItems = flowOf(successPagingData).collectAsLazyPagingItems()
                CharactersContent(
                    characters = pagingItems,
                    searchState = CharactersSearchState.Active("", emptyList()),
                    onLoadedCharacterChanged = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Character: Rick Sanchez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Character: Birdperson")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Character: Mr. Meeseeks")
            .assertIsDisplayed()
    }

    @Test
    fun characterList_displaysCharacterNames() {
        val characters = listOf(
            CharacterTestFactory.createAliveCharacter(),
            CharacterTestFactory.createDeadCharacter()
        )

        val successPagingData = PagingData.from(
            data = characters,
            sourceLoadStates = LoadStates(
                refresh = LoadState.NotLoading(endOfPaginationReached = false),
                prepend = LoadState.NotLoading(endOfPaginationReached = true),
                append = LoadState.NotLoading(endOfPaginationReached = false)
            )
        )

        composeTestRule.setContent {
            ArkanoTestTheme {
                val pagingItems = flowOf(successPagingData).collectAsLazyPagingItems()
                CharactersContent(
                    characters = pagingItems,
                    searchState = CharactersSearchState.Active("", emptyList()),
                    onLoadedCharacterChanged = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Rick Sanchez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Birdperson")
            .assertIsDisplayed()
    }
}