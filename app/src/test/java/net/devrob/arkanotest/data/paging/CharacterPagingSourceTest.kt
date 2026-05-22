package net.devrob.arkanotest.data.paging

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.testing.TestPager
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import net.devrob.arkanotest.data.remote.api.CharacterApiService
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.testutil.CharacterDtoFactory
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class CharacterPagingSourceTest {
    private lateinit var apiService: CharacterApiService
    private lateinit var pagingSource: CharacterPagingSource

    @Before
    fun setup() {
        apiService = mockk()
        pagingSource = CharacterPagingSource(apiService)
    }

    @Test
    fun `load returns Page when API call is successful`() = runTest {
        val response = CharacterDtoFactory.createFirstPageResponse()
        coEvery { apiService.getCharacters(1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(20, page.data.size)
    }

    @Test
    fun `load returns correct characters mapped from DTOs`() = runTest {
        val response = CharacterDtoFactory.createCharacterResponseDto(
            results = listOf(CharacterDtoFactory.createAliveCharacterDto())
        )
        coEvery { apiService.getCharacters(1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertEquals(1, page.data.size)
        assertEquals("Rick Sanchez", page.data[0].name)
    }

    @Test
    fun `load first page has null prevKey`() = runTest {
        val response = CharacterDtoFactory.createFirstPageResponse()
        coEvery { apiService.getCharacters(1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertNull(page.prevKey)
    }

    @Test
    fun `load first page has nextKey of 2 when more pages exist`() = runTest {
        val response = CharacterDtoFactory.createFirstPageResponse()
        coEvery { apiService.getCharacters(1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertEquals(2, page.nextKey)
    }

    @Test
    fun `load middle page has correct prevKey and nextKey`() = runTest {
        val response = CharacterDtoFactory.createMiddlePageResponse(page = 5)
        coEvery { apiService.getCharacters(5) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 5,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertEquals(4, page.prevKey)
        assertEquals(6, page.nextKey)
    }

    @Test
    fun `load last page has null nextKey`() = runTest {
        val response = CharacterDtoFactory.createLastPageResponse()
        coEvery { apiService.getCharacters(42) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 42,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertNull(page.nextKey)
    }

    @Test
    fun `load returns Error when API throws IOException`() = runTest {
        coEvery { apiService.getCharacters(1) } throws IOException("Network error")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
        val error = result as PagingSource.LoadResult.Error
        assertTrue(error.throwable is IOException)
    }

    @Test
    fun `load returns Error when API throws RuntimeException`() = runTest {
        coEvery { apiService.getCharacters(1) } throws RuntimeException("Unexpected error")

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        assertTrue(result is PagingSource.LoadResult.Error)
    }

    @Test
    fun `load uses page 1 when key is null`() = runTest {
        val response = CharacterDtoFactory.createFirstPageResponse()
        coEvery { apiService.getCharacters(1) } returns response

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        coVerify { apiService.getCharacters(1) }
    }

    @Test
    fun `load uses provided key for page parameter`() = runTest {
        val response = CharacterDtoFactory.createMiddlePageResponse(page = 3)
        coEvery { apiService.getCharacters(3) } returns response

        pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 3,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        coVerify { apiService.getCharacters(3) }
    }

    @Test
    fun `refresh with TestPager loads first page correctly`() = runTest {
        val response = CharacterDtoFactory.createFirstPageResponse()
        coEvery { apiService.getCharacters(1) } returns response

        val pager = TestPager(
            config = PagingConfig(pageSize = 20),
            pagingSource = pagingSource
        )

        val result = pager.refresh()

        assertTrue(result is PagingSource.LoadResult.Page)
        val page = result as PagingSource.LoadResult.Page
        assertEquals(20, page.data.size)
    }

    @Test
    fun `append with TestPager loads next page correctly`() = runTest {
        val firstPageResponse = CharacterDtoFactory.createFirstPageResponse()
        val secondPageResponse = CharacterDtoFactory.createMiddlePageResponse(page = 2)
        coEvery { apiService.getCharacters(1) } returns firstPageResponse
        coEvery { apiService.getCharacters(2) } returns secondPageResponse

        val pager = TestPager(
            config = PagingConfig(pageSize = 20),
            pagingSource = pagingSource
        )

        pager.refresh()
        val appendResult = pager.append()

        assertTrue(appendResult is PagingSource.LoadResult.Page)
        val page = appendResult as PagingSource.LoadResult.Page
        assertEquals(20, page.data.size)
    }

    @Test
    fun `getRefreshKey returns null when pages list is empty`() {
        val pagingState = PagingState<Int, Character>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 20),
            leadingPlaceholderCount = 0
        )

        val refreshKey = pagingSource.getRefreshKey(pagingState)

        assertNull(refreshKey)
    }

    @Test
    fun `load handles empty results list`() = runTest {
        val response = CharacterDtoFactory.createCharacterResponseDto(
            info = CharacterDtoFactory.createInfoDto(next = null, prev = null),
            results = emptyList()
        )
        coEvery { apiService.getCharacters(1) } returns response

        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 20,
                placeholdersEnabled = false
            )
        )

        val page = result as PagingSource.LoadResult.Page
        assertTrue(page.data.isEmpty())
        assertNull(page.nextKey)
    }
}