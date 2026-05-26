package net.devrob.arkanotest.data.mapper

import net.devrob.arkanotest.data.remote.dto.CharacterDto
import net.devrob.arkanotest.data.remote.dto.LocationDto
import net.devrob.arkanotest.domain.model.CharacterStatus
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterDetailMapperTest {
    @Test
    fun `toCharacterDetail maps all fields correctly`() {
        val dto = createCharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            species = "Human",
            type = "Scientist",
            gender = "Male",
            originName = "Earth (C-137)",
            locationName = "Citadel of Ricks",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg",
            episodeUrls = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2"
            ),
            created = "2017-11-04T18:48:46.250Z"
        )

        val result = dto.toCharacterDetail()

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
        assertEquals(CharacterStatus.ALIVE, result.status)
        assertEquals("Human", result.species)
        assertEquals("Scientist", result.type)
        assertEquals("Male", result.gender)
        assertEquals("Earth (C-137)", result.origin.name)
        assertEquals("Citadel of Ricks", result.location.name)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", result.image)
        assertEquals(2, result.episodeCount)
        assertEquals("2017-11-04T18:48:46.250Z", result.created)
    }

    @Test
    fun `toCharacterDetail maps status alive correctly`() {
        val dto = createCharacterDto(status = "Alive")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toCharacterDetail maps status alive lowercase correctly`() {
        val dto = createCharacterDto(status = "alive")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toCharacterDetail maps status alive uppercase correctly`() {
        val dto = createCharacterDto(status = "ALIVE")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toCharacterDetail maps status dead correctly`() {
        val dto = createCharacterDto(status = "Dead")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.DEAD, result.status)
    }

    @Test
    fun `toCharacterDetail maps status dead lowercase correctly`() {
        val dto = createCharacterDto(status = "dead")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.DEAD, result.status)
    }

    @Test
    fun `toCharacterDetail maps status dead uppercase correctly`() {
        val dto = createCharacterDto(status = "DEAD")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.DEAD, result.status)
    }

    @Test
    fun `toCharacterDetail maps status unknown correctly`() {
        val dto = createCharacterDto(status = "unknown")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toCharacterDetail maps status unknown uppercase correctly`() {
        val dto = createCharacterDto(status = "UNKNOWN")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toCharacterDetail maps empty status to unknown`() {
        val dto = createCharacterDto(status = "")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toCharacterDetail maps unexpected status to unknown`() {
        val dto = createCharacterDto(status = "suspended")

        val result = dto.toCharacterDetail()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toCharacterDetail maps episode count correctly with multiple episodes`() {
        val dto = createCharacterDto(
            episodeUrls = listOf(
                "https://rickandmortyapi.com/api/episode/1",
                "https://rickandmortyapi.com/api/episode/2",
                "https://rickandmortyapi.com/api/episode/3",
                "https://rickandmortyapi.com/api/episode/4",
                "https://rickandmortyapi.com/api/episode/5"
            )
        )

        val result = dto.toCharacterDetail()

        assertEquals(5, result.episodeCount)
    }

    @Test
    fun `toCharacterDetail maps episode count correctly with single episode`() {
        val dto = createCharacterDto(
            episodeUrls = listOf("https://rickandmortyapi.com/api/episode/1")
        )

        val result = dto.toCharacterDetail()

        assertEquals(1, result.episodeCount)
    }

    @Test
    fun `toCharacterDetail maps episode count correctly with empty list`() {
        val dto = createCharacterDto(episodeUrls = emptyList())

        val result = dto.toCharacterDetail()

        assertEquals(0, result.episodeCount)
    }

    @Test
    fun `toCharacterDetail maps episode count correctly with many episodes`() {
        val episodeUrls = (1..51).map { "https://rickandmortyapi.com/api/episode/$it" }
        val dto = createCharacterDto(episodeUrls = episodeUrls)

        val result = dto.toCharacterDetail()

        assertEquals(51, result.episodeCount)
    }

    @Test
    fun `toCharacterDetail maps origin location info correctly`() {
        val dto = createCharacterDto(
            originName = "Earth (C-137)",
            originUrl = "https://rickandmortyapi.com/api/location/1"
        )

        val result = dto.toCharacterDetail()

        assertEquals("Earth (C-137)", result.origin.name)
        assertEquals("https://rickandmortyapi.com/api/location/1", result.origin.url)
    }

    @Test
    fun `toCharacterDetail maps location info correctly`() {
        val dto = createCharacterDto(
            locationName = "Citadel of Ricks",
            locationUrl = "https://rickandmortyapi.com/api/location/3"
        )

        val result = dto.toCharacterDetail()

        assertEquals("Citadel of Ricks", result.location.name)
        assertEquals("https://rickandmortyapi.com/api/location/3", result.location.url)
    }

    @Test
    fun `toCharacterDetail handles empty type field`() {
        val dto = createCharacterDto(type = "")

        val result = dto.toCharacterDetail()

        assertEquals("", result.type)
    }

    @Test
    fun `toCharacterDetail handles special characters in name`() {
        val dto = createCharacterDto(name = "Mr. Poopybutthole")

        val result = dto.toCharacterDetail()

        assertEquals("Mr. Poopybutthole", result.name)
    }

    private fun createCharacterDto(
        id: Int = 1,
        name: String = "Rick Sanchez",
        status: String = "Alive",
        species: String = "Human",
        type: String = "",
        gender: String = "Male",
        originName: String = "Earth (C-137)",
        originUrl: String = "https://rickandmortyapi.com/api/location/1",
        locationName: String = "Citadel of Ricks",
        locationUrl: String = "https://rickandmortyapi.com/api/location/3",
        image: String = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
        episodeUrls: List<String> = listOf(
            "https://rickandmortyapi.com/api/episode/1",
            "https://rickandmortyapi.com/api/episode/2"
        ),
        url: String = "https://rickandmortyapi.com/api/character/$id",
        created: String = "2017-11-04T18:48:46.250Z"
    ): CharacterDto {
        return CharacterDto(
            id = id,
            name = name,
            status = status,
            species = species,
            type = type,
            gender = gender,
            origin = LocationDto(name = originName, url = originUrl),
            location = LocationDto(name = locationName, url = locationUrl),
            image = image,
            episode = episodeUrls,
            url = url,
            created = created
        )
    }
}