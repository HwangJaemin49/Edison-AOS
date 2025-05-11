package com.umc.edison.data.bound

fun <DomainType, DataType> flowDataResource(
    dataAction: suspend () -> DataType,
) = FlowBoundResource<DomainType, DataType>(
    dataAction = dataAction,
)

fun <DomainType, LocalDataType, RemoteDataType> flowSyncDataResource(
    localAction: suspend () -> LocalDataType,
    remoteSync: suspend () -> RemoteDataType,
    onRemoteSuccess: (suspend (RemoteDataType) -> Unit)? = null,
) = FlowSyncBoundResource<DomainType, LocalDataType, RemoteDataType>(
    localAction = localAction,
    remoteSync = remoteSync,
    onRemoteSuccess = onRemoteSuccess
)