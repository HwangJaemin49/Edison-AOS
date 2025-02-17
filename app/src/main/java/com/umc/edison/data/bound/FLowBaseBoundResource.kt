package com.umc.edison.data.bound

import android.util.Log
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector

abstract class FLowBaseBoundResource<DomainType, DataType>(
    val dataAction: suspend () -> DataType
) : Flow<DataResource<DomainType>> {
    private val TAG = dataAction.toString()

    protected open suspend fun actionFromSource(
        collector: FlowCollector<DataResource<DomainType>>,
        successAction: (suspend (DataType) -> Unit)? = null,
    ) {
        collector.emit(DataResource.loading())
        try {
            val data = dataAction()
            Log.d(TAG, "actionFromSource result: $data")
            collector.emit(DataResource.success(data.toDomain()))

            successAction?.invoke(data)
        } catch (e: Throwable) {
            collector.emit(DataResource.error(e))
        }
    }
}
