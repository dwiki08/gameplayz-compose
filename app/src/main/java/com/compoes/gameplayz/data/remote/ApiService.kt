package com.compoes.gameplayz.data.remote

import com.compoes.gameplayz.data.remote.response.GameDetailResponse
import com.compoes.gameplayz.data.remote.response.GamesResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("games")
    suspend fun getGames(
        @Query("ordering") ordering: String? = null,
        @Query("dates") dates: String? = null,
        @Query("page") page: Int? = 1,
        @Query("page_size") pageSize: Int? = 20,
    ): GamesResponse

    @GET("games/{gameId}")
    suspend fun getGameDetail(@Path("gameId") gameId: String): GameDetailResponse

    @GET("games")
    suspend fun searchGames(@Query("search") search: String): GamesResponse
}