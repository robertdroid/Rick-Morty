package net.devrob.arkanotest.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterDetail

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
    suspend fun getCharacterById(id: Int): CharacterDetail
}