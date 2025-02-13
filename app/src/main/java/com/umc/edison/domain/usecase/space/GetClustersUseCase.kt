package com.umc.edison.domain.usecase.space

import com.umc.edison.domain.DataResource
import com.umc.edison.domain.model.Cluster
import com.umc.edison.domain.repository.BubbleRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetClustersUseCase @Inject constructor(
    private val bubbleRepository: BubbleRepository
) {
    operator fun invoke(): Flow<DataResource<List<Cluster>>> = bubbleRepository.getClusters()
}