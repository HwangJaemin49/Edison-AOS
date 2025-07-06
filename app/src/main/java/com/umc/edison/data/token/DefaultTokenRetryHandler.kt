package com.umc.edison.data.token

import com.google.android.gms.common.api.ApiException
import com.umc.edison.data.datasources.UserRemoteDataSource
import javax.inject.Inject

class DefaultTokenRetryHandler @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val tokenManager: TokenManager
) : TokenRetryHandler {

    override suspend fun <T> runWithTokenRetry(dataAction: suspend () -> T): T {
        return try {
            dataAction()
        } catch (e: ApiException) {
            if (e.message != "LOGIN4004") throw e

            val refreshToken = tokenManager.loadRefreshToken() ?: throw IllegalStateException("No refresh token")

            val newAccessToken = userRemoteDataSource.refreshAccessToken(refreshToken)
            tokenManager.setToken(newAccessToken, refreshToken)

            dataAction()
        }
    }
}
