package com.umc.edison.data.token

interface TokenRetryHandler {
    suspend fun <T> runWithTokenRetry(
        dataAction: suspend () -> T,
    ): T
}