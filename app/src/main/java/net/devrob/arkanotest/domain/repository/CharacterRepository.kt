package net.devrob.arkanotest.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.devrob.arkanotest.domain.model.Character

interface CharacterRepository {
    fun getCharacters(): Flow<PagingData<Character>>
}