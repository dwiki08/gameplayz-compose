package com.compoes.gameplayz.data.repository

import arrow.core.Either
import com.compoes.gameplayz.data.local.dao.GameDao
import com.compoes.gameplayz.data.mapper.toEntity
import com.compoes.gameplayz.data.mapper.toModel
import com.compoes.gameplayz.data.remote.ApiService
import com.compoes.gameplayz.domain.model.ErrorResult
import com.compoes.gameplayz.domain.model.Game
import com.compoes.gameplayz.domain.repository.GamesRepository
import com.compoes.gameplayz.utils.ext.toGeneralError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

class GamesRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gameDao: GameDao
) : GamesRepository {

    private val favorites = MutableStateFlow<Set<Int>>(setOf())

    override suspend fun getGames(
        ordering: String?,
        dates: String?,
        page: Int?,
        pageSize: Int?
    ): Either<ErrorResult, List<Game>> {
        return Either.catch {
            val call = apiService.getGames(ordering, dates, page, pageSize)
            call.results.map { it.toModel() }
        }.mapLeft { it.toGeneralError() }
    }

    override suspend fun getGameDetail(id: Int): Either<ErrorResult, Game> {
        return Either.catch {
            var game = apiService.getGameDetail(id.toString()).toModel()
            if (gameDao.getGame(id) != null)
                game = game.copy(isFavorite = true)
            return@catch game
        }.mapLeft { it.toGeneralError() }
    }

    override suspend fun searchGames(query: String): Either<ErrorResult, List<Game>> {
        return Either.catch {
            if (query.isNotEmpty()) {
                val call = apiService.searchGames(query)
                call.results.map {
                    it.toModel()
                }
            } else {
                listOf()
            }
        }.mapLeft {
            it.toGeneralError()
        }
    }

    override suspend fun getFavorites(): Either<ErrorResult, List<Game>> {
        return Either.catch {
            val games = gameDao.getFavorites()
            games.map { it.id }.forEach { id -> id?.let { favorites.update { it.plus(id) } } }
            games.map { it.toModel() }
        }.mapLeft {
            it.toGeneralError()
        }
    }

    override suspend fun addFavorite(game: Game) {
        gameDao.insertFavorites(game.toEntity())
        favorites.update {
            it.plus(game.id)
        }
    }

    override suspend fun removeFavorite(id: Int) {
        gameDao.deleteFavorite(id)
        favorites.update {
            it.minus(id)
        }
    }

    override fun observeFavorites(): Flow<Set<Int>> {
        return favorites
    }
}