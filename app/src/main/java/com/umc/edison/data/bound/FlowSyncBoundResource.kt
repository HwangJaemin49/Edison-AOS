package com.umc.edison.data.bound

import android.util.Log
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

class FlowSyncBoundResource<DomainType, DataType, BackUpDataType>(
    localAction: suspend () -> DataType,
    private val remoteSync: suspend () -> BackUpDataType,
    private val onRemoteSuccess: (suspend (BackUpDataType) -> Unit)? = null,
) : FLowBaseBoundResource<DomainType, DataType>(localAction) {
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        actionFromSource(collector)
        actionBackUpFromSource()
    }

    private suspend fun actionBackUpFromSource() {
        try {
            val data = withContext(Dispatchers.IO) { remoteSync() }
            Log.d("actionBackUpFromSource", "result: $data")

            withContext(Dispatchers.IO) {
                data?.let {
                    onRemoteSuccess?.invoke(it)
                }
            }
        } catch (e: Throwable) {
            Log.e("actionBackUpFromSource", "error: ${e.message}")
        }
    }
}
