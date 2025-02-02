package com.umc.edison.domain.usecase.mypage

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetDeletedBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(): Flow<DataResource<List<Bubble>>> = bubbleRepository.getDeletedBubbles()
}