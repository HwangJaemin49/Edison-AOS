package com.umc.edison.domain.repository

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import kotlinx.coroutines.flow.Flow

interface BubbleRepository {
    fun getAllBubbles(): Flow<DataResource<List<Bubble>>>
}