package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBubblesByLabelUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(labelId: String): Flow<DataResource<List<Bubble>>> = bubbleRepository.getBubblesByLabel(labelId)
}