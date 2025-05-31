package com.compoes.gameplayz.data.remote.response

import com.google.gson.annotations.SerializedName

data class GenresItemResponse(

    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("slug")
    val slug: String
)