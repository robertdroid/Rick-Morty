package net.devrob.arkanotest.domain.usecase

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharactersUseCase @Inject constructor(
    private val characterRepository: CharacterRepository
) {

    operator fun invoke(): Flow<PagingData<Character>> {
        return characterRepository.getCharacters()
    }
}