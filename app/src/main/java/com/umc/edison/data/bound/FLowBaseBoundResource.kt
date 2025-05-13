package com.umc.edison.data.bound

import android.util.Log
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

abstract class FLowBaseBoundResource<DomainType, DataType>(
    val dataAction: suspend () -> DataType,
) : Flow<DataResource<DomainType>> {
    protected open suspend fun actionFromSource(
        collector: FlowCollector<DataResource<DomainType>>,
    ) {
        collector.emit(DataResource.loading())
        try {
            val data = withContext(Dispatchers.IO) { dataAction() }
            Log.d("actionFromSource", "result: $data")
            collector.emit(DataResource.success(data.toDomain()))
        } catch (e: Throwable) {
            collector.emit(DataResource.error(e))
        }
    }
}
