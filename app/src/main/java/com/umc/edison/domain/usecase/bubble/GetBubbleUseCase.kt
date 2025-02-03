package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBubbleUseCase @Inject constructor( private val bubbleRepository: BubbleRepository)
{
    operator fun invoke(bubbleId: Int): Flow<DataResource<Bubble>> = bubbleRepository.getBubbleDetail(bubbleId)
}