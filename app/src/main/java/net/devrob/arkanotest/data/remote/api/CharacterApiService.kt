package net.devrob.arkanotest.data.remote.api

import net.devrob.arkanotest.data.remote.dto.CharacterDto
import net.devrob.arkanotest.data.remote.dto.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterApiService {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ) : CharacterResponseDto

    @GET("character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): CharacterDto
}