package com.umc.edison.domain.usecase.bubble

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetSearchBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(query: String): Flow<DataResource<List<Bubble>>> = bubbleRepository.getSearchBubbles(query)
}