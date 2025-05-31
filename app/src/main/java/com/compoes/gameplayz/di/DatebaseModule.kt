package com.compoes.gameplayz.di

import android.content.Context
import androidx.room.Room
import com.compoes.gameplayz.BuildConfig
import com.compoes.gameplayz.data.local.GameDatabase
import com.compoes.gameplayz.data.local.dao.GameDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideDatabase(@ApplicationContext context: Context): GameDatabase {
        return Room.databaseBuilder(context, GameDatabase::class.java, BuildConfig.DATABASE)
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideGameDao(database: GameDatabase): GameDao = database.gameDao()
}