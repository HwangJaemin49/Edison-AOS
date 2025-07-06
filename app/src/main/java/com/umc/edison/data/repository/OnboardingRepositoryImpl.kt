package com.umc.edison.data.repository

import com.umc.edison.data.bound.FlowBoundResourceFactory
import com.umc.edison.data.datasources.PrefDataSource
import com.umc.edison.domain.DataResource
import com.umc.edison.domain.repository.OnboardingRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor(
    private val prefDataSource: PrefDataSource,
    private val resourceFactory: FlowBoundResourceFactory,
) : OnboardingRepository {
    override fun hasSeen(screen: String): Flow<DataResource<Boolean>> =
        resourceFactory.local(
            dataAction = { prefDataSource.get(screen, false) }
        )

    override fun setSeen(screen: String): Flow<DataResource<Unit>> =
        resourceFactory.local(
            dataAction = { prefDataSource.set(screen, true) }
        )
}