package com.umc.edison.domain.usecase.recentSearch

import com.umc.edison.data.DataResource
import com.umc.edison.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DeleteRecentSearchUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) {
    operator fun invoke(keyword: String): Flow<DataResource<Unit>> =
        recentSearchRepository.deleteRecentSearch(keyword)
}