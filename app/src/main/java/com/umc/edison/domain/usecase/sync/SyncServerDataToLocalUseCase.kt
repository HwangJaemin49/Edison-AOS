package com.umc.edison.domain.usecase.sync

import com.umc.edison.domain.repository.SyncRepository
import javax.inject.Inject

class SyncServerDataToLocalUseCase @Inject constructor(
    private val syncRepository: SyncRepository
) {
    suspend operator fun invoke() = syncRepository.syncServerDataToLocal()
}