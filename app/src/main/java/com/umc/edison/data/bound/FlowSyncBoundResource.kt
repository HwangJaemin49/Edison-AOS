package com.umc.edison.data.bound

import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.FlowCollector

class FlowSyncBoundResource<DomainType, DataType, BackUpDataType>(
    localAction: suspend () -> DataType,
    remoteSync: suspend () -> BackUpDataType,
    private val onRemoteSuccess: (suspend (BackUpDataType) -> Unit)? = null,
) : FLowBaseBoundResource<DomainType, DataType, BackUpDataType>(
    dataAction = localAction,
    backUpAction = remoteSync
) {
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        actionFromSource(collector)
        actionBackUpFromSource(collector, onRemoteSuccess)
    }
}
