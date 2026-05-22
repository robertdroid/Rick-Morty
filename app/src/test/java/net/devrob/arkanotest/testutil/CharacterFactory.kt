package net.devrob.arkanotest.testutil

import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterStatus

object CharacterFactory {

    fun create(
        id: Int = 1,
        name: String = "Rick Sanchez",
        status: CharacterStatus = CharacterStatus.ALIVE,
        imageUrl: String = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg"
    ): Character {
        return Character(
            id = id,
            name = name,
            status = status,
            imageUrl = imageUrl
        )
    }

    fun createList(count: Int = 20, startId: Int = 1): List<Character> {
        return (startId until startId + count).map { id ->
            create(
                id = id,
                name = "Character $id",
                status = when (id % 3) {
                    0 -> CharacterStatus.ALIVE
                    1 -> CharacterStatus.DEAD
                    else -> CharacterStatus.UNKNOWN
                }
            )
        }
    }

    fun createRickSanchez(): Character = create(
        id = 1,
        name = "Rick Sanchez",
        status = CharacterStatus.ALIVE
    )

    fun createMortySmith(): Character = create(
        id = 2,
        name = "Morty Smith",
        status = CharacterStatus.ALIVE
    )

    fun createDeadCharacter(): Character = create(
        id = 3,
        name = "Birdperson",
        status = CharacterStatus.DEAD
    )

    fun createUnknownStatusCharacter(): Character = create(
        id = 4,
        name = "Mr. Meeseeks",
        status = CharacterStatus.UNKNOWN
    )
}