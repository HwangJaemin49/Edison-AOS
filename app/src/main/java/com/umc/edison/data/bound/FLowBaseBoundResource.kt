package com.umc.edison.data.bound

import android.util.Log
import com.umc.edison.data.model.toDomain
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

abstract class FLowBaseBoundResource<DomainType, DataType, BackUpDataType>(
    val dataAction: suspend () -> DataType,
    val backUpAction: suspend () -> BackUpDataType? = { null },
) : Flow<DataResource<DomainType>> {
    private val TAG = dataAction.toString()

    protected open suspend fun actionFromSource(
        collector: FlowCollector<DataResource<DomainType>>,
    ) {
        collector.emit(DataResource.loading())
        try {
            val data = withContext(Dispatchers.IO) { dataAction() }
            Log.d(TAG, "actionFromSource result: $data")
            collector.emit(DataResource.success(data.toDomain()))
        } catch (e: Throwable) {
            collector.emit(DataResource.error(e))
        }
    }

    protected open suspend fun actionBackUpFromSource(
        collector: FlowCollector<DataResource<DomainType>>,
        successAction: (suspend (BackUpDataType) -> Unit)? = null,
    ) {
        try {
            val data = withContext(Dispatchers.IO) { backUpAction() }
            Log.d(TAG, "actionBackUpFromSource result: $data")

            withContext(Dispatchers.IO) {
                data?.let {
                    successAction?.invoke(it)
                }
            }
        } catch (e: Throwable) {
            Log.e(TAG, "actionBackUpFromSource error: ${e.message}")
        }
    }
}
