package net.devrob.arkanotest.presentation.components

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.devrob.arkanotest.ui.theme.ArkanoTestTheme
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EmptySearchStateTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun emptySearchState_displaysQueryInMessage() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "XYZ123")
            }
        }

        composeTestRule
            .onNodeWithText("No results match \"XYZ123\"")
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchState_hasProperAccessibility() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "test")
            }
        }

        composeTestRule
            .onNodeWithContentDescription("No results found for test")
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchState_displaysNoCharactersFoundTitle() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "unknown")
            }
        }

        composeTestRule
            .onNodeWithText("No characters found")
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchState_displaysCorrectMessageForDifferentQueries() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "Rick Sanchez Jr")
            }
        }

        composeTestRule
            .onNodeWithText("No results match \"Rick Sanchez Jr\"")
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchState_displaysCorrectMessageForEmptyQuery() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "")
            }
        }

        composeTestRule
            .onNodeWithText("No results match \"\"")
            .assertIsDisplayed()
    }

    @Test
    fun emptySearchState_displaysCorrectMessageForSpecialCharacters() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                EmptySearchState(query = "@#\$%")
            }
        }

        composeTestRule
            .onNodeWithText("No results match \"@#\$%\"")
            .assertIsDisplayed()
    }
}