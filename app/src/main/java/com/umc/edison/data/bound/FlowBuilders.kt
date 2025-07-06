package com.umc.edison.data.bound

import com.umc.edison.data.token.TokenRetryHandler

fun <DomainType, DataType> flowDataResource(
    dataAction: suspend () -> DataType,
) = FlowBoundResource<DomainType, DataType>(
    dataAction = dataAction,
)

fun <DomainType, LocalDataType, RemoteDataType> flowSyncDataResource(
    localAction: suspend () -> LocalDataType,
    remoteSync: suspend () -> RemoteDataType,
    onRemoteSuccess: (suspend (RemoteDataType) -> Unit)? = null,
    retryHandler: TokenRetryHandler
) = FlowSyncBoundResource<DomainType, LocalDataType, RemoteDataType>(
    localAction = localAction,
    remoteSync = remoteSync,
    onRemoteSuccess = onRemoteSuccess,
    retryHandler = retryHandler
)

fun <DomainType, DataType> flowRemoteDataResource(
    dataAction: suspend () -> DataType,
    retryHandler: TokenRetryHandler
) = FlowRemoteBoundResource<DomainType, DataType>(
    dataAction = dataAction,
    retryHandler = retryHandler
)