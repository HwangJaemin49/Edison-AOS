package com.umc.edison.data.bound

import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.FlowCollector

class FlowBoundResource<DomainType, DataType>(
    dataAction: suspend () -> DataType,
) : FlowBaseBoundResource<DomainType, DataType>(dataAction) {
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        actionFromSource(collector)
    }
}
