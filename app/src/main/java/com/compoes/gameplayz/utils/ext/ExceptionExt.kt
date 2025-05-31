package com.compoes.gameplayz.utils.ext

import com.compoes.gameplayz.domain.model.ErrorResult
import okio.IOException
import retrofit2.HttpException

fun Throwable.toGeneralError(): ErrorResult {
    var code: Int? = null
    val error = when (this) {
        is IOException -> ErrorResult.ErrorMsg.NetworkError
        is HttpException -> {
            code = this.code()
            ErrorResult.ErrorMsg.UnknownResponse
        }

        else -> ErrorResult.ErrorMsg.UnknownError
    }

    return ErrorResult(
        error = error,
        code = code,
        t = this
    )
}
