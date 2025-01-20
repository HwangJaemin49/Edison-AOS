package com.umc.edison.data.bound

fun <DomainType, DataType> flowDataResource(
    dataAction: suspend () -> DataType,
) = FlowBoundResource<DomainType, DataType>(
    dataAction = dataAction,
)

fun <DomainType, DataType> flowDataResource(
    remoteDataAction: suspend () -> DataType,
    localDataAction: suspend () -> DataType,
    saveCacheAction: suspend (DataType) -> Unit,
) = FlowCachingBoundResource<DomainType, DataType>(
    remoteDataAction = remoteDataAction,
    localDataAction = localDataAction,
    saveCacheAction = saveCacheAction,
)
