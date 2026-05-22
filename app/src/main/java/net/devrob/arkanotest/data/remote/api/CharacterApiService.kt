package net.devrob.arkanotest.data.remote.api

import net.devrob.arkanotest.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : CharacterResponseDto
}