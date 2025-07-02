package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.Flow

interface OnboardingRepository {
    fun hasSeen(screen: String): Flow<DataResource<Boolean>>
    fun setSeen(screen: String): Flow<DataResource<Unit>>
}