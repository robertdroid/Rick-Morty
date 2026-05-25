package net.devrob.arkanotest.presentation.characters

import androidx.paging.PagingData
import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.devrob.arkanotest.domain.usecase.GetCharactersUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterStatus

@OptIn(ExperimentalCoroutinesApi::class)
class CharactersViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCharactersUseCase: GetCharactersUseCase
    private lateinit var viewModel: CharactersViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCharactersUseCase = mockk<GetCharactersUseCase>()
        every { getCharactersUseCase.invoke() } returns flowOf(PagingData.empty<Character>())
        viewModel = CharactersViewModel(getCharactersUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `updateSearchQuery updates searchQuery StateFlow`() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.updateSearchQuery("Rick")

            assertEquals("Rick", awaitItem())
        }
    }

    @Test
    fun `clearSearch resets searchQuery to empty`() = runTest {
        viewModel.searchQuery.test {
            assertEquals("", awaitItem())

            viewModel.updateSearchQuery("Rick")
            assertEquals("Rick", awaitItem())

            viewModel.clearSearch()
            assertEquals("", awaitItem())
        }
    }

    @Test
    fun `searchQuery initial value is empty`() = runTest {
        assertTrue(viewModel.searchQuery.value.isEmpty())
    }

    @Test
    fun `searchState initial value is Inactive`() = runTest {
        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)
        }
    }

    @Test
    fun `searchState is Inactive when search query is blank`() = runTest {
        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateSearchQuery("")
            advanceTimeBy(350)

            expectNoEvents()
        }
    }

    @Test
    fun `searchState is Active with filtered results when query is not blank`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1"),
            Character(2, "Morty Smith", CharacterStatus.ALIVE, "url2"),
            Character(3, "Summer Smith", CharacterStatus.ALIVE, "url3")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("Rick")
            advanceTimeBy(350)

            val state = awaitItem()
            assertTrue(state is CharactersSearchState.Active)
            val activeState = state as CharactersSearchState.Active
            assertEquals("Rick", activeState.query)
            assertEquals(1, activeState.results.size)
            assertEquals("Rick Sanchez", activeState.results[0].name)
        }
    }

    @Test
    fun `searchState Active isEmpty is true when no matches found`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1"),
            Character(2, "Morty Smith", CharacterStatus.ALIVE, "url2")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("ZZZZZ")
            advanceTimeBy(350)

            val state = awaitItem() as CharactersSearchState.Active
            assertTrue(state.isEmpty)
            assertTrue(state.results.isEmpty())
        }
    }

    @Test
    fun `filter is case insensitive`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("rick")
            advanceTimeBy(350)

            val state = awaitItem() as CharactersSearchState.Active
            assertEquals(1, state.results.size)
        }
    }

    @Test
    fun `filter matches partial names`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1"),
            Character(2, "Morty Smith", CharacterStatus.ALIVE, "url2"),
            Character(3, "Summer Smith", CharacterStatus.ALIVE, "url3"),
            Character(4, "Beth Smith", CharacterStatus.ALIVE, "url4")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("Smith")
            advanceTimeBy(350)

            val state = awaitItem() as CharactersSearchState.Active
            assertEquals(3, state.results.size)
        }
    }

    @Test
    fun `updateLoadedCharacters updates internal state`() = runTest {
        val characters = listOf(
            Character(1, "Rick", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("Rick")
            advanceTimeBy(350)

            val state = awaitItem() as CharactersSearchState.Active
            assertEquals(1, state.results.size)
        }
    }

    @Test
    fun `searchState debounces by 300ms`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("R")
            viewModel.updateSearchQuery("Ri")
            viewModel.updateSearchQuery("Ric")
            viewModel.updateSearchQuery("Rick")

            advanceTimeBy(299)
            expectNoEvents()

            advanceTimeBy(2)
            assertTrue(awaitItem() is CharactersSearchState.Active)
        }
    }

    @Test
    fun `clearSearch returns searchState to Inactive`() = runTest {
        val characters = listOf(
            Character(1, "Rick", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("Rick")
            advanceTimeBy(350)

            assertTrue(awaitItem() is CharactersSearchState.Active)

            viewModel.clearSearch()
            advanceTimeBy(350)

            assertTrue(awaitItem() is CharactersSearchState.Inactive)
        }
    }

    @Test
    fun `rapid typing only emits final value after debounce`() = runTest {
        val characters = listOf(
            Character(1, "Morty Smith", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("M")
            advanceTimeBy(100)
            viewModel.updateSearchQuery("Mo")
            advanceTimeBy(100)
            viewModel.updateSearchQuery("Mor")
            advanceTimeBy(100)
            viewModel.updateSearchQuery("Mort")
            advanceTimeBy(100)
            viewModel.updateSearchQuery("Morty")

            advanceTimeBy(301)
            val state = awaitItem()
            assertTrue(state is CharactersSearchState.Active)
            assertEquals("Morty", (state as CharactersSearchState.Active).query)
        }
    }

    @Test
    fun `searchState Active isEmpty is false when matches found`() = runTest {
        val characters = listOf(
            Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1")
        )

        viewModel.searchState.test {
            assertTrue(awaitItem() is CharactersSearchState.Inactive)

            viewModel.updateLoadedCharacters(characters)
            viewModel.updateSearchQuery("Rick")
            advanceTimeBy(350)

            val state = awaitItem() as CharactersSearchState.Active
            assertFalse(state.isEmpty)
        }
    }
}