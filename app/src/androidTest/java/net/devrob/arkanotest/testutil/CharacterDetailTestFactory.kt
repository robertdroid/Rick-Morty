package net.devrob.arkanotest.testutil

import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.LocationInfo

object CharacterDetailTestFactory {
    fun create(
        id: Int = 1,
        name: String = "Rick Sanchez",
        status: CharacterStatus = CharacterStatus.ALIVE,
        species: String = "Human",
        type: String = "",
        gender: String = "Male",
        originName: String = "Earth (C-137)",
        originUrl: String = "https://rickandmortyapi.com/api/location/1",
        locationName: String = "Citadel of Ricks",
        locationUrl: String = "https://rickandmortyapi.com/api/location/3",
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
            origin = LocationInfo(name = originName, url = originUrl),
            location = LocationInfo(name = locationName, url = locationUrl),
            image = image,
            episodeCount = episodeCount,
            created = created
        )
    }

    fun createRickSanchez(): CharacterDetail = create(
        id = 1,
        name = "Rick Sanchez",
        status = CharacterStatus.ALIVE,
        species = "Human",
        gender = "Male",
        originName = "Earth (C-137)",
        locationName = "Citadel of Ricks",
        episodeCount = 51
    )

    fun createMortySmith(): CharacterDetail = create(
        id = 2,
        name = "Morty Smith",
        status = CharacterStatus.ALIVE,
        species = "Human",
        gender = "Male",
        originName = "unknown",
        locationName = "Citadel of Ricks",
        episodeCount = 51
    )

    fun createDeadCharacter(): CharacterDetail = create(
        id = 3,
        name = "Birdperson",
        status = CharacterStatus.DEAD,
        species = "Bird-Person",
        gender = "Male",
        originName = "Bird World",
        locationName = "unknown",
        episodeCount = 8
    )

    fun createUnknownStatusCharacter(): CharacterDetail = create(
        id = 4,
        name = "Mr. Meeseeks",
        status = CharacterStatus.UNKNOWN,
        species = "Meeseeks",
        gender = "Male",
        originName = "unknown",
        locationName = "unknown",
        episodeCount = 5
    )

    fun createWithType(): CharacterDetail = create(
        id = 5,
        name = "Abadango Cluster Princess",
        status = CharacterStatus.ALIVE,
        species = "Alien",
        type = "Royalty",
        gender = "Female",
        originName = "Abadango",
        locationName = "Abadango",
        episodeCount = 1
    )
}