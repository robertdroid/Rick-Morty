package net.devrob.arkanotest.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.devrob.arkanotest.data.mapper.toDomain
import net.devrob.arkanotest.data.remote.api.CharacterApiService
import net.devrob.arkanotest.domain.model.Character

class CharacterPagingSource(
    private val apiService: CharacterApiService
) : PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val page = params.key ?: 1
        return try {
            val response = apiService.getCharacters(page)
            val characters = response.results.map { it.toDomain() }
            LoadResult.Page(
                data = characters,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.info.next == null) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}