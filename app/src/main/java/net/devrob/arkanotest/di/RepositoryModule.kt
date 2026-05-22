package net.devrob.arkanotest.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import net.devrob.arkanotest.data.repository.CharacterRepositoryImpl
import net.devrob.arkanotest.domain.repository.CharacterRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCharacterRepository(
        characterRepositoryImpl: CharacterRepositoryImpl
    ) : CharacterRepository
}