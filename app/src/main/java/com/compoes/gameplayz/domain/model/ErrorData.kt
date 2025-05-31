package com.compoes.gameplayz.domain.model

data class ErrorResult(
    val error: ErrorMsg,
    val code: Int? = null,
    val t: Throwable? = null
) {
    enum class ErrorMsg(var message: String) {
        NetworkError("Network error"),
        UnknownResponse("HTTP error"),
        UnknownError("Unknown error")
    }
}