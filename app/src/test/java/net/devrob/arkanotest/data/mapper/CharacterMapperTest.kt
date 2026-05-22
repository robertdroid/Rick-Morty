package net.devrob.arkanotest.data.mapper

import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.testutil.CharacterDtoFactory
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterMapperTest {
    @Test
    fun `toDomain maps id correctly`() {
        val dto = CharacterDtoFactory.createCharacterDto(id = 42)

        val result = dto.toDomain()

        assertEquals(42, result.id)
    }

    @Test
    fun `toDomain maps name correctly`() {
        val dto = CharacterDtoFactory.createCharacterDto(name = "Morty Smith")

        val result = dto.toDomain()

        assertEquals("Morty Smith", result.name)
    }

    @Test
    fun `toDomain maps imageUrl from image field`() {
        val expectedUrl = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        val dto = CharacterDtoFactory.createCharacterDto(image = expectedUrl)

        val result = dto.toDomain()

        assertEquals(expectedUrl, result.imageUrl)
    }

    @Test
    fun `toDomain maps Alive status to ALIVE`() {
        val dto = CharacterDtoFactory.createAliveCharacterDto()

        val result = dto.toDomain()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toDomain maps Dead status to DEAD`() {
        val dto = CharacterDtoFactory.createDeadCharacterDto()

        val result = dto.toDomain()

        assertEquals(CharacterStatus.DEAD, result.status)
    }

    @Test
    fun `toDomain maps unknown status to UNKNOWN`() {
        val dto = CharacterDtoFactory.createUnknownStatusCharacterDto()

        val result = dto.toDomain()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toDomain maps status case-insensitively for ALIVE`() {
        val dto = CharacterDtoFactory.createMixedCaseStatusCharacterDto()

        val result = dto.toDomain()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toDomain maps lowercase alive status correctly`() {
        val dto = CharacterDtoFactory.createCharacterDto(status = "alive")

        val result = dto.toDomain()

        assertEquals(CharacterStatus.ALIVE, result.status)
    }

    @Test
    fun `toDomain maps lowercase dead status correctly`() {
        val dto = CharacterDtoFactory.createCharacterDto(status = "dead")

        val result = dto.toDomain()

        assertEquals(CharacterStatus.DEAD, result.status)
    }

    @Test
    fun `toDomain maps empty status to UNKNOWN`() {
        val dto = CharacterDtoFactory.createEmptyStatusCharacterDto()

        val result = dto.toDomain()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toDomain maps unexpected status to UNKNOWN`() {
        val dto = CharacterDtoFactory.createCharacterDto(status = "suspended")

        val result = dto.toDomain()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toDomain maps random string status to UNKNOWN`() {
        val dto = CharacterDtoFactory.createCharacterDto(status = "xyz123")

        val result = dto.toDomain()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }

    @Test
    fun `toDomain maps complete character correctly`() {
        val dto = CharacterDtoFactory.createCharacterDto(
            id = 1,
            name = "Rick Sanchez",
            status = "Alive",
            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
        )

        val result = dto.toDomain()

        assertEquals(1, result.id)
        assertEquals("Rick Sanchez", result.name)
        assertEquals(CharacterStatus.ALIVE, result.status)
        assertEquals("https://rickandmortyapi.com/api/character/avatar/1.jpeg", result.imageUrl)
    }

    @Test
    fun `toDomain preserves special characters in name`() {
        val dto = CharacterDtoFactory.createCharacterDto(name = "Mr. Poopybutthole")

        val result = dto.toDomain()

        assertEquals("Mr. Poopybutthole", result.name)
    }

    @Test
    fun `toDomain handles whitespace in status`() {
        val dto = CharacterDtoFactory.createCharacterDto(status = " alive ")

        val result = dto.toDomain()

        assertEquals(CharacterStatus.UNKNOWN, result.status)
    }
}