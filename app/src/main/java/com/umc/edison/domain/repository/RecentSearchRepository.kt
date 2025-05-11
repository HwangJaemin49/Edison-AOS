package com.umc.edison.domain.repository

import com.umc.edison.data.DataResource
import kotlinx.coroutines.flow.Flow

interface RecentSearchRepository {
    // READ
    fun getAllRecentSearches(): Flow<DataResource<List<String>>>

    // DELETE
    fun deleteRecentSearch(search: String): Flow<DataResource<Unit>>
}