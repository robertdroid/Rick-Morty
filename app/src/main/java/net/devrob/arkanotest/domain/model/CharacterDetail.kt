package net.devrob.arkanotest.domain.model

data class CharacterDetail(
    val id: Int,
    val name: String,
    val status: CharacterStatus,
    val species: String,
    val type: String,
    val gender: String,
    val origin: LocationInfo,
    val location: LocationInfo,
    val image: String,
    val episodeCount: Int,
    val created: String
)

data class LocationInfo(
    val name: String,
    val url: String
)