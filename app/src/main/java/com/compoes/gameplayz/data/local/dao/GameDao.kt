package com.compoes.gameplayz.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.compoes.gameplayz.data.local.enitity.GameEntity

@Dao
interface GameDao {
    @Query("SELECT * FROM tb_game")
    suspend fun getFavorites(): List<GameEntity>

    @Query("SELECT * FROM tb_game WHERE game_id = :id")
    suspend fun getGame(id: Int): GameEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorites(vararg entity: GameEntity)

    @Query("DELETE FROM tb_game WHERE game_id = :id")
    suspend fun deleteFavorite(id: Int)
}