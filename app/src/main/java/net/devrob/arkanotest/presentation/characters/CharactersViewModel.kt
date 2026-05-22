package net.devrob.arkanotest.presentation.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import net.devrob.arkanotest.domain.model.Character
import net.devrob.arkanotest.domain.usecase.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(
    getCharactersUseCase: GetCharactersUseCase
) : ViewModel() {

    val charactersPagingData: Flow<PagingData<Character>> =
        getCharactersUseCase().cachedIn(viewModelScope)

}