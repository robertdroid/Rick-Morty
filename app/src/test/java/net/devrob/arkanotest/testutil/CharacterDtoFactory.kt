package net.devrob.arkanotest.testutil

import net.devrob.arkanotest.data.remote.dto.CharacterDto
import net.devrob.arkanotest.data.remote.dto.CharacterResponseDto
import net.devrob.arkanotest.data.remote.dto.InfoDto
import net.devrob.arkanotest.data.remote.dto.LocationDto

object CharacterDtoFactory {

    fun createCharacterDto(
        id: Int = 1,
        name: String = "Rick Sanchez",
        status: String = "Alive",
        species: String = "Human",
        type: String = "",
        gender: String = "Male",
        origin: LocationDto = createLocationDto("Earth (C-137)"),
        location: LocationDto = createLocationDto("Citadel of Ricks"),
        image: String = "https://rickandmortyapi.com/api/character/avatar/$id.jpeg",
        episode: List<String> = listOf(
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
            origin = origin,
            location = location,
            image = image,
            episode = episode,
            url = url,
            created = created
        )
    }

    fun createLocationDto(
        name: String = "Earth (C-137)",
        url: String = "https://rickandmortyapi.com/api/location/1"
    ): LocationDto {
        return LocationDto(
            name = name,
            url = url
        )
    }

    fun createInfoDto(
        count: Int = 826,
        pages: Int = 42,
        next: String? = "https://rickandmortyapi.com/api/character?page=2",
        prev: String? = null
    ): InfoDto {
        return InfoDto(
            count = count,
            pages = pages,
            next = next,
            prev = prev
        )
    }

    fun createCharacterResponseDto(
        info: InfoDto = createInfoDto(),
        results: List<CharacterDto> = createCharacterDtoList()
    ): CharacterResponseDto {
        return CharacterResponseDto(
            info = info,
            results = results
        )
    }

    fun createCharacterDtoList(count: Int = 20, startId: Int = 1): List<CharacterDto> {
        return (startId until startId + count).map { id ->
            createCharacterDto(
                id = id,
                name = "Character $id",
                status = when (id % 3) {
                    0 -> "Alive"
                    1 -> "Dead"
                    else -> "unknown"
                }
            )
        }
    }

    fun createFirstPageResponse(): CharacterResponseDto {
        return createCharacterResponseDto(
            info = createInfoDto(
                next = "https://rickandmortyapi.com/api/character?page=2",
                prev = null
            ),
            results = createCharacterDtoList(count = 20, startId = 1)
        )
    }

    fun createMiddlePageResponse(page: Int = 2): CharacterResponseDto {
        return createCharacterResponseDto(
            info = createInfoDto(
                next = "https://rickandmortyapi.com/api/character?page=${page + 1}",
                prev = "https://rickandmortyapi.com/api/character?page=${page - 1}"
            ),
            results = createCharacterDtoList(count = 20, startId = (page - 1) * 20 + 1)
        )
    }

    fun createLastPageResponse(page: Int = 42): CharacterResponseDto {
        return createCharacterResponseDto(
            info = createInfoDto(
                next = null,
                prev = "https://rickandmortyapi.com/api/character?page=${page - 1}"
            ),
            results = createCharacterDtoList(count = 6, startId = (page - 1) * 20 + 1)
        )
    }

    fun createAliveCharacterDto(): CharacterDto = createCharacterDto(
        id = 1,
        name = "Rick Sanchez",
        status = "Alive"
    )

    fun createDeadCharacterDto(): CharacterDto = createCharacterDto(
        id = 2,
        name = "Birdperson",
        status = "Dead"
    )

    fun createUnknownStatusCharacterDto(): CharacterDto = createCharacterDto(
        id = 3,
        name = "Mr. Meeseeks",
        status = "unknown"
    )

    fun createMixedCaseStatusCharacterDto(): CharacterDto = createCharacterDto(
        id = 4,
        name = "Summer Smith",
        status = "ALIVE"
    )

    fun createEmptyStatusCharacterDto(): CharacterDto = createCharacterDto(
        id = 5,
        name = "Unknown Entity",
        status = ""
    )

}