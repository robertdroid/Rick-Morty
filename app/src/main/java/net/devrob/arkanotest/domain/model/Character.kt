package net.devrob.arkanotest.domain.model

data class Character(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val imageUrl: String
)
