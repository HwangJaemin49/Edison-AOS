package com.umc.edison.data.bound

import android.util.Log
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

class FlowCachingBoundResource<DomainType, DataType>(
    remoteDataAction: suspend () -> DataType,
    private val localDataAction: suspend () -> DataType,
    private val saveCacheAction: suspend (DataType) -> Unit,
) : FLowBaseBoundResource<DomainType, DataType>(remoteDataAction) {

    private val TAG = localDataAction.toString()

    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        withContext(Dispatchers.IO) {
            val result: DataType? = try {
                localDataAction()
            } catch (e: Exception) {
                null
            }

            Log.d(TAG, "collect result: $result")
            val isEmptyResult = when (result) {
                is Collection<*> -> result.isEmpty()
                is Map<*, *> -> result.isEmpty()
                else -> result == null
            }

            if (isEmptyResult) {
                actionFromSource(collector, saveCacheAction)
            } else {
                collector.emit(DataResource.success(result.toDomain()))
            }
        }
    }
}