package com.umc.edison.data.bound

import com.umc.edison.data.token.TokenRetryHandler
import com.umc.edison.domain.DataResource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FlowBoundResourceFactory @Inject constructor(
    private val retryHandler: TokenRetryHandler
) {
    fun <DomainType, DataType> local(
        dataAction: suspend () -> DataType
    ): Flow<DataResource<DomainType>> = flowDataResource(dataAction)

    fun <DomainType, LocalDataType, RemoteDataType> sync(
        localAction: suspend () -> LocalDataType,
        remoteSync: suspend () -> RemoteDataType,
        onRemoteSuccess: (suspend (RemoteDataType) -> Unit)? = null
    ): Flow<DataResource<DomainType>> =
        flowSyncDataResource(localAction, remoteSync, onRemoteSuccess, retryHandler)

    fun <DomainType, DataType> remote(
        dataAction: suspend () -> DataType
    ): Flow<DataResource<DomainType>> = flowRemoteDataResource(dataAction, retryHandler)
}