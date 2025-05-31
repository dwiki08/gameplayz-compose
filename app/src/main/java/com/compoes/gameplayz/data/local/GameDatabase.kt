package com.compoes.gameplayz.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.compoes.gameplayz.data.local.dao.GameDao
import com.compoes.gameplayz.data.local.enitity.GameEntity

@Database(
    entities = [GameEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GameDatabase : RoomDatabase() {
    abstract fun gameDao(): GameDao
}