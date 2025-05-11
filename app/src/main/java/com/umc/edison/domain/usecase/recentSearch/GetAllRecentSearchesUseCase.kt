package com.umc.edison.domain.usecase.recentSearch

import com.umc.edison.data.DataResource
import com.umc.edison.domain.repository.RecentSearchRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllRecentSearchesUseCase @Inject constructor(
    private val recentSearchRepository: RecentSearchRepository
) {
    operator fun invoke(): Flow<DataResource<List<String>>> =
        recentSearchRepository.getAllRecentSearches()
}