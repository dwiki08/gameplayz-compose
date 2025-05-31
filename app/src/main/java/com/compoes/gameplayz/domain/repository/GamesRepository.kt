package com.compoes.gameplayz.domain.repository

import arrow.core.Either
import com.compoes.gameplayz.domain.model.ErrorResult
import com.compoes.gameplayz.domain.model.Game
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    suspend fun getGames(
        ordering: String? = null,
        dates: String? = null,
        page: Int? = 1,
        pageSize: Int? = 10
    ): Either<ErrorResult, List<Game>>

    suspend fun getGameDetail(id: Int): Either<ErrorResult, Game>

    suspend fun searchGames(query: String): Either<ErrorResult, List<Game>>

    suspend fun getFavorites(): Either<ErrorResult, List<Game>>

    suspend fun addFavorite(game: Game)

    suspend fun removeFavorite(id: Int)

    fun observeFavorites(): Flow<Set<Int>>
}