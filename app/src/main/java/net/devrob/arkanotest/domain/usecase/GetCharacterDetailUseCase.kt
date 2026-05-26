package net.devrob.arkanotest.domain.usecase

import net.devrob.arkanotest.domain.model.CharacterDetail
import net.devrob.arkanotest.domain.repository.CharacterRepository
import javax.inject.Inject

class GetCharacterDetailUseCase @Inject constructor(
    private val repository: CharacterRepository
) {
    suspend operator fun invoke(id: Int): CharacterDetail {
        return repository.getCharacterById(id)
    }
}