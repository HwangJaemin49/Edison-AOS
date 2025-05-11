package com.umc.edison.domain.usecase.bubble

import com.umc.edison.data.DataResource
import com.umc.edison.domain.model.bubble.Bubble
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(query: String): Flow<DataResource<List<Bubble>>> =
        bubbleRepository.searchBubbles(query)
}
