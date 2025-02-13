package com.umc.edison.domain.usecase.space

import android.util.Log
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.ClusteredBubbles
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClusteredBubblesUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(): Flow<DataResource<List<ClusteredBubbles>>> {
        val result = bubbleRepository.getClusteredBubbles()
        Log.d("GetClusteredBubblesUseCase", "invoke result: $result")
        return result
    }
}