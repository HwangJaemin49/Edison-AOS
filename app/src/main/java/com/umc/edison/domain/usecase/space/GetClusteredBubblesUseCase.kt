package com.umc.edison.domain.usecase.space

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ClusteredBubblePosition
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClusteredBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(): Flow<DataResource<List<ClusteredBubblePosition>>> = bubbleRepository.getClusteredBubblesPosition()
}