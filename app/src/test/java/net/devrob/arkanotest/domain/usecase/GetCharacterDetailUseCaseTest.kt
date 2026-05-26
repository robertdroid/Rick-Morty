package net.devrob.arkanotest.domain.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.LocationInfo
import net.devrob.arkanotest.domain.repository.CharacterRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Before
import org.junit.Test

class GetCharacterDetailUseCaseTest {

    private lateinit var repository: CharacterRepository
    private lateinit var useCase: GetCharacterDetailUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetCharacterDetailUseCase(repository)
    }

    @Test
    fun `invoke returns character detail from repository`() = runTest {
        val expectedCharacter = createTestCharacterDetail()
        coEvery { repository.getCharacterById(1) } returns expectedCharacter

        val result = useCase(1)

        assertEquals(expectedCharacter, result)
        coVerify(exactly = 1) { repository.getCharacterById(1) }
    }

    @Test
    fun `invoke propagates exception from repository`() = runTest {
        val expectedException = RuntimeException("Network error")
        coEvery { repository.getCharacterById(1) } throws expectedException

        val exception = assertThrows(RuntimeException::class.java) {
            kotlinx.coroutines.runBlocking { useCase(1) }
        }

        assertEquals("Network error", exception.message)
    }

    @Test
    fun `invoke passes correct character id to repository`() = runTest {
        val characterId = 42
        val expectedCharacter = createTestCharacterDetail(id = characterId)
        coEvery { repository.getCharacterById(characterId) } returns expectedCharacter

        val result = useCase(characterId)

        assertEquals(characterId, result.id)
        coVerify(exactly = 1) { repository.getCharacterById(characterId) }
    }

    @Test
    fun `invoke returns different characters for different ids`() = runTest {
        val character1 = createTestCharacterDetail(id = 1, name = "Rick Sanchez")
        val character2 = createTestCharacterDetail(id = 2, name = "Morty Smith")

        coEvery { repository.getCharacterById(1) } returns character1
        coEvery { repository.getCharacterById(2) } returns character2

        val result1 = useCase(1)
        val result2 = useCase(2)

        assertEquals("Rick Sanchez", result1.name)
        assertEquals("Morty Smith", result2.name)
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