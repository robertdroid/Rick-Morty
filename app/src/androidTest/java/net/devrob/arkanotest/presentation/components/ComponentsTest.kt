package net.devrob.arkanotest.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.testutil.CharacterTestFactory
import net.devrob.arkanotest.ui.theme.ArkanoTestTheme
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComponentsTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun characterListItem_displaysNameAndStatus() {
        val character = CharacterTestFactory.createAliveCharacter()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterItem(character = character)
            }
        }

        composeTestRule
            .onNodeWithText("Rick Sanchez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Character: Rick Sanchez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Image of Rick Sanchez")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Alive")
            .assertIsDisplayed()
    }

    @Test
    fun characterListItem_displaysDeadCharacter() {
        val character = CharacterTestFactory.createDeadCharacter()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterItem(character = character)
            }
        }

        composeTestRule
            .onNodeWithText("Birdperson")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Dead")
            .assertIsDisplayed()
    }

    @Test
    fun characterListItem_displaysUnknownStatusCharacter() {
        val character = CharacterTestFactory.createUnknownStatusCharacter()

        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterItem(character = character)
            }
        }

        composeTestRule
            .onNodeWithText("Mr. Meeseeks")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Unknown")
            .assertIsDisplayed()
    }

    @Test
    fun characterStatusBadge_showsAliveStatus() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterStatusBadge(status = CharacterStatus.ALIVE)
            }
        }

        composeTestRule
            .onNodeWithText("Alive")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Status: Alive")
            .assertIsDisplayed()
    }

    @Test
    fun characterStatusBadge_showsDeadStatus() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterStatusBadge(status = CharacterStatus.DEAD)
            }
        }

        composeTestRule
            .onNodeWithText("Dead")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Status: Dead")
            .assertIsDisplayed()
    }

    @Test
    fun characterStatusBadge_showsUnknownStatus() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                CharacterStatusBadge(status = CharacterStatus.UNKNOWN)
            }
        }

        composeTestRule
            .onNodeWithText("Unknown")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Status: Unknown")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_displaysMessageAndRetryButton() {
        val errorMessage = "Something went wrong"

        composeTestRule.setContent {
            ArkanoTestTheme {
                ErrorState(
                    message = errorMessage,
                    onRetry = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText(errorMessage)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithContentDescription("Error: $errorMessage")
            .assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonIsClickable() {
        var retryClicked = false

        composeTestRule.setContent {
            ArkanoTestTheme {
                ErrorState(
                    message = "Error occurred",
                    onRetry = { retryClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Retry")
            .performClick()

        assertTrue("Retry callback should be invoked", retryClicked)
    }

    @Test
    fun loadingState_displaysLoadingIndicator() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                LoadingState()
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Loading content")
            .assertIsDisplayed()
    }
}