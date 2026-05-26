package net.devrob.arkanotest.data.mapper

import net.devrob.arkanotest.data.remote.dto.CharacterDto
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.model.CharacterStatus
import net.devrob.arkanotest.domain.model.LocationInfo

fun CharacterDto.toCharacterDetail(): CharacterDetail {
    return CharacterDetail(
        id = id,
        name = name,
        status = when (status.lowercase()) {
            "alive" -> CharacterStatus.ALIVE
            "dead" -> CharacterStatus.DEAD
            else -> CharacterStatus.UNKNOWN
        },
        species = species,
        type = type,
        gender = gender,
        origin = LocationInfo(
            name = origin.name,
            url = origin.url
        ),
        location = LocationInfo(
            name = location.name,
            url = location.url
        ),
        image = image,
        episodeCount = episode.size,
        created = created
    )
}