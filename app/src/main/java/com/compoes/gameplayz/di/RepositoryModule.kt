package com.compoes.gameplayz.di

import com.compoes.gameplayz.data.repository.GamesRepositoryImpl
import com.compoes.gameplayz.domain.repository.GamesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun providesGamesRepository(impl: GamesRepositoryImpl): GamesRepository
}