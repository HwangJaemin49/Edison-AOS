package com.umc.edison.data.bound

import com.umc.edison.data.model.toDomain
import com.umc.edison.data.token.TokenRetryHandler
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.FlowCollector

class FlowRemoteBoundResource<DomainType, DataType>(
    dataAction: suspend () -> DataType,
    private val retryHandler: TokenRetryHandler,
) : FLowBaseBoundResource<DomainType, DataType>(dataAction) {
    override suspend fun collect(collector: FlowCollector<DataResource<DomainType>>) {
        collector.emit(DataResource.loading())

        try {
            val result = retryHandler.runWithTokenRetry { dataAction() }
            collector.emit(DataResource.success(result.toDomain()))
        } catch (e: Throwable) {
            collector.emit(DataResource.error(e))
        }
    }
}
