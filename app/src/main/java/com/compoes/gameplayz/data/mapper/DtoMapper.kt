package com.compoes.gameplayz.data.mapper

import com.compoes.gameplayz.data.remote.response.GameDetailResponse
import com.compoes.gameplayz.data.remote.response.GenresItemResponse
import com.compoes.gameplayz.data.remote.response.ParentPlatformsItemResponse
import com.compoes.gameplayz.domain.model.Game

fun GameDetailResponse.toModel(): Game {
    return Game(
        this.id,
        this.name,
        this.descriptionRaw ?: "",
        this.released,
        this.backgroundImage ?: "",
        platformDtoToDomain(this.parentPlatforms),
        genresDtoToString(this.genres),
        this.ratingsCount,
        this.rating ?: 0f,
        false
    )
}

private fun genresDtoToString(data: List<GenresItemResponse>?): String {
    val result = StringBuilder().append("")
    if (data != null) {
        for (i in data.indices) {
            if (i < data.size - 1) {
                result.append("${data[i].name}, ")
            } else {
                result.append(data[i].name)
            }
        }
    }
    return result.toString()
}

private fun platformDtoToDomain(data: List<ParentPlatformsItemResponse>?): List<Game.Platform> {
    val platform = mutableListOf<Game.Platform>()
    data?.forEach { p ->
        when (p.platform.name.uppercase()) {
            "PC" -> platform.add(Game.Platform.PC)
            "PLAYSTATION" -> platform.add(Game.Platform.PLAYSTATION)
            "XBOX" -> platform.add(Game.Platform.XBOX)
            "MACOS" -> platform.add(Game.Platform.MAC_OS)
        }
    }
    return platform
}