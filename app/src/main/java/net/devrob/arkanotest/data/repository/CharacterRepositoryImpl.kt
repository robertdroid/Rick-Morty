package net.devrob.arkanotest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.devrob.arkanotest.data.mapper.toCharacterDetail
import net.devrob.arkanotest.data.paging.CharacterPagingSource
import net.devrob.arkanotest.data.remote.api.CharacterApiService
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.repository.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(
    private val apiService: CharacterApiService
) : CharacterRepository {

    override fun getCharacters(): Flow<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                initialLoadSize = 40,
                prefetchDistance = 10,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CharacterPagingSource(apiService) }
        ).flow
    }

    override suspend fun getCharacterById(id: Int): CharacterDetail {
        return apiService.getCharacterById(id).toCharacterDetail()
    }
}