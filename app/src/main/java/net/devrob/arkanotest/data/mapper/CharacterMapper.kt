package net.devrob.arkanotest.data.mapper

import net.devrob.arkanotest.data.remote.dto.CharacterDto
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterStatus

fun CharacterDto.toDomain(): Character {
    return Character (
        id = id,
        name = name,
        status = when (status.lowercase()) {
            "alive" -> CharacterStatus.ALIVE
            "dead" -> CharacterStatus.DEAD
            else -> CharacterStatus.UNKNOWN
        },
        imageUrl = image
    )
}