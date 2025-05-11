package com.umc.edison.data.bound

import com.umc.edison.domain.DataResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.withContext

class FlowBoundResource<DomainType, DataType>(
    dataAction: suspend () -> DataType,
) : FLowBaseBoundResource<DomainType, DataType, DataType>(dataAction) {
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        actionFromSource(collector)
    }
}
