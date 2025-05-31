package com.compoes.gameplayz.data.mapper

import com.compoes.gameplayz.data.local.enitity.GameEntity
import com.compoes.gameplayz.domain.model.Game
import com.compoes.gameplayz.utils.ext.toEmptyOrString

fun Game.toEntity(): GameEntity {
    return GameEntity(
        id = id,
        name = name,
        description = description,
        releaseDate = releaseDate ?: "",
        posterImage = posterImage,
        platforms = platforms.joinToString { it.value },
        genres = genres,
        ratingsCount = ratingsCount,
        rating = rating
    )
}

fun GameEntity.toModel(): Game {
    return Game(
        id = id ?: 0,
        name = name.toEmptyOrString(),
        description = description.toEmptyOrString(),
        releaseDate = releaseDate.toEmptyOrString(),
        posterImage = posterImage.toEmptyOrString(),
        platforms = platforms.toEmptyOrString().split(",").map { Game.Platform.valueOf(it.trim()) },
        genres = genres.toEmptyOrString(),
        ratingsCount = ratingsCount ?: 0,
        rating = rating ?: 0f,
        isFavorite = true
    )
}