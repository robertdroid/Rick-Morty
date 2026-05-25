package net.devrob.arkanotest.presentation.components

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.devrob.arkanotest.ui.theme.ArkanoTestTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchTextFieldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchTextField_showsPlaceholder_whenEmpty() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "",
                    onQueryChange = {},
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Search characters...")
            .assertIsDisplayed()
    }

    @Test
    fun searchTextField_showsClearButton_whenNotEmpty() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "Rick",
                    onQueryChange = {},
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .assertIsDisplayed()
    }

    @Test
    fun searchTextField_hidesClearButton_whenEmpty() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "",
                    onQueryChange = {},
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .assertDoesNotExist()
    }

    @Test
    fun searchTextField_callsOnClearClick_whenClearButtonPressed() {
        var clearClicked = false

        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "Rick",
                    onQueryChange = {},
                    onClearClick = { clearClicked = true }
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Clear search")
            .performClick()

        assertTrue("Clear callback should be invoked", clearClicked)
    }

    @Test
    fun searchTextField_callsOnQueryChange_whenTextEntered() {
        var receivedQuery = ""

        composeTestRule.setContent {
            ArkanoTestTheme {
                var query by mutableStateOf("")
                SearchTextField(
                    query = query,
                    onQueryChange = {
                        query = it
                        receivedQuery = it
                    },
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Search characters")
            .performTextInput("Morty")

        assertEquals("Morty", receivedQuery)
    }

    @Test
    fun searchTextField_displaysEnteredText() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "Summer",
                    onQueryChange = {},
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithText("Summer")
            .assertIsDisplayed()
    }

    @Test
    fun searchTextField_hasProperAccessibility() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "",
                    onQueryChange = {},
                    onClearClick = {}
                )
            }
        }

        composeTestRule
            .onNodeWithContentDescription("Search characters")
            .assertIsDisplayed()
    }

    @Test
    fun searchTextField_showsCustomPlaceholder() {
        composeTestRule.setContent {
            ArkanoTestTheme {
                SearchTextField(
                    query = "",
                    onQueryChange = {},
                    onClearClick = {},
                    placeholder = "Find characters..."
                )
            }
        }

        composeTestRule
            .onNodeWithText("Find characters...")
            .assertIsDisplayed()
    }
}