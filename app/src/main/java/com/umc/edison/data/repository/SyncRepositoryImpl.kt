package com.umc.edison.data.repository

import com.umc.edison.domain.repository.SyncRepository
import javax.inject.Inject

class SyncRepositoryImpl @Inject constructor(
) : SyncRepository {
    override suspend fun syncLocalDataToServer() {
        TODO("Not yet implemented")
    }

    override suspend fun syncServerDataToLocal() {
        TODO("Not yet implemented")
    }

}