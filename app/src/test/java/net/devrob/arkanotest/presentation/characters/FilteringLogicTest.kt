package net.devrob.arkanotest.presentation.characters

import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.testutil.CharacterFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class FilteringLogicTest {

    private val characters = listOf(
        Character(1, "Rick Sanchez", CharacterStatus.ALIVE, "url1"),
        Character(2, "Morty Smith", CharacterStatus.ALIVE, "url2"),
        Character(3, "Summer Smith", CharacterStatus.ALIVE, "url3"),
        Character(4, "Beth Smith", CharacterStatus.ALIVE, "url4"),
        Character(5, "Jerry Smith", CharacterStatus.ALIVE, "url5")
    )

    @Test
    fun `filter by name is case insensitive`() {
        val query = "rick"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(1, result.size)
        assertEquals("Rick Sanchez", result[0].name)
    }

    @Test
    fun `filter returns multiple matches`() {
        val query = "smith"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(4, result.size)
        assertTrue(result.all { it.name.contains("Smith") })
    }

    @Test
    fun `empty query returns original list`() {
        val query = ""

        val result = if (query.isBlank()) characters
        else characters.filter { it.name.contains(query, ignoreCase = true) }

        assertEquals(5, result.size)
    }

    @Test
    fun `no matches returns empty list`() {
        val query = "xyz"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertTrue(result.isEmpty())
    }

    @Test
    fun `filter matches partial names`() {
        val query = "San"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(1, result.size)
        assertEquals("Rick Sanchez", result[0].name)
    }

    @Test
    fun `filter with uppercase query still matches`() {
        val query = "RICK"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(1, result.size)
        assertEquals("Rick Sanchez", result[0].name)
    }

    @Test
    fun `filter with mixed case query still matches`() {
        val query = "rIcK"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(1, result.size)
        assertEquals("Rick Sanchez", result[0].name)
    }

    @Test
    fun `whitespace only query treated as inactive search`() {
        val query = "   "

        val result = if (query.isBlank()) characters
        else characters.filter { it.name.contains(query, ignoreCase = true) }

        assertEquals(5, result.size)
    }

    @Test
    fun `filter with single character query works`() {
        val query = "r"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        // Rick, Morty, Summer, Jerry all contain 'r'
        assertEquals(4, result.size)
    }

    @Test
    fun `filter preserves order of matching items`() {
        val query = "smith"

        val result = characters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals("Morty Smith", result[0].name)
        assertEquals("Summer Smith", result[1].name)
        assertEquals("Beth Smith", result[2].name)
        assertEquals("Jerry Smith", result[3].name)
    }

    @Test
    fun `filter on empty list returns empty list`() {
        val emptyCharacters = emptyList<Character>()
        val query = "rick"

        val result = emptyCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertTrue(result.isEmpty())
    }

    @Test
    fun `filter using CharacterFactory created characters`() {
        val factoryCharacters = CharacterFactory.createList(count = 5)
        val query = "Character 1"

        val result = factoryCharacters.filter {
            it.name.contains(query, ignoreCase = true)
        }

        assertEquals(1, result.size)
        assertEquals("Character 1", result[0].name)
    }
}