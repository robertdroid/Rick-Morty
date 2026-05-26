package net.devrob.arkanotest.presentation.characterdetail

import androidx.lifecycle.SavedStateHandle
import app.cash.turbine.test
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.LocationInfo
import net.devrob.arkanotest.domain.usecase.GetCharacterDetailUseCase
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CharacterDetailViewModelTest {
    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getCharacterDetailUseCase: GetCharacterDetailUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getCharacterDetailUseCase = mockk()
        savedStateHandle = SavedStateHandle(mapOf("characterId" to 1))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is Loading`() = runTest {
        coEvery { getCharacterDetailUseCase.invoke(any()) } returns createTestCharacterDetail()

        val viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterDetailUseCase)

        assertTrue(viewModel.uiState.value is CharacterDetailUiState.Loading)
    }

    @Test
    fun `loadCharacterDetail success updates state to Success`() = runTest {
        val expectedCharacter = createTestCharacterDetail()
        coEvery { getCharacterDetailUseCase.invoke(1) } returns expectedCharacter

        val viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterDetailUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is CharacterDetailUiState.Loading)

            advanceUntilIdle()

            val successState = awaitItem()
            assertTrue(successState is CharacterDetailUiState.Success)
            assertEquals(expectedCharacter, (successState as CharacterDetailUiState.Success).character)
        }
    }

    @Test
    fun `loadCharacterDetail error updates state to Error`() = runTest {
        val errorMessage = "Network error"
        coEvery { getCharacterDetailUseCase.invoke(1) } throws Exception(errorMessage)

        val viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterDetailUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is CharacterDetailUiState.Loading)

            advanceUntilIdle()

            val errorState = awaitItem()
            assertTrue(errorState is CharacterDetailUiState.Error)
            assertEquals(errorMessage, (errorState as CharacterDetailUiState.Error).message)
        }
    }

    @Test
    fun `retry after error reloads character detail`() = runTest {
        val errorMessage = "Network error"
        val expectedCharacter = createTestCharacterDetail()

        coEvery { getCharacterDetailUseCase.invoke(1) } throws Exception(errorMessage)

        val viewModel = CharacterDetailViewModel(savedStateHandle, getCharacterDetailUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is CharacterDetailUiState.Loading)
            advanceUntilIdle()
            assertTrue(awaitItem() is CharacterDetailUiState.Error)

            coEvery { getCharacterDetailUseCase.invoke(1) } returns expectedCharacter

            viewModel.loadCharacterDetail()

            assertTrue(awaitItem() is CharacterDetailUiState.Loading)
            advanceUntilIdle()

            val successState = awaitItem()
            assertTrue(successState is CharacterDetailUiState.Success)
            assertEquals(expectedCharacter, (successState as CharacterDetailUiState.Success).character)
        }
    }

    @Test
    fun `characterId is extracted from SavedStateHandle`() = runTest {
        val characterId = 42
        val customSavedStateHandle = SavedStateHandle(mapOf("characterId" to characterId))
        val expectedCharacter = createTestCharacterDetail(id = characterId)

        coEvery { getCharacterDetailUseCase.invoke(characterId) } returns expectedCharacter

        val viewModel = CharacterDetailViewModel(customSavedStateHandle, getCharacterDetailUseCase)

        viewModel.uiState.test {
            assertTrue(awaitItem() is CharacterDetailUiState.Loading)
            advanceUntilIdle()

            val successState = awaitItem()
            assertTrue(successState is CharacterDetailUiState.Success)
            assertEquals(characterId, (successState as CharacterDetailUiState.Success).character.id)
        }
    }

    @Test(expected = IllegalStateException::class)
    fun `missing characterId in SavedStateHandle throws exception`() {
        val emptySavedStateHandle = SavedStateHandle()
        coEvery { getCharacterDetailUseCase.invoke(any()) } returns createTestCharacterDetail()

        CharacterDetailViewModel(emptySavedStateHandle, getCharacterDetailUseCase)
    }

    private fun createTestCharacterDetail(
        id: Int = 1,
        name: String = "Rick Sanchez",
        status: CharacterStatus = CharacterStatus.ALIVE,
        species: String = "Human",
        type: String = "",
        gender: String = "Male",
        originName: String = "Earth (C-137)",
        locationName: String = "Citadel of Ricks",
        image: String = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
        episodeCount: Int = 51,
        created: String = "2017-11-04T18:48:46.250Z"
    ): CharacterDetail {
        return CharacterDetail(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = LocationInfo(
                name = originName,
                url = "https://rickandmortyapi.com/api/location/1"
            ),
            location = LocationInfo(
                name = locationName,
                url = "https://rickandmortyapi.com/api/location/3"
            ),
            image = image,
            episodeCount = episodeCount,
            created = created
        )
    }
}