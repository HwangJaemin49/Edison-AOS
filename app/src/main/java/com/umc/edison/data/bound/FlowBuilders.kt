package com.umc.edison.data.bound

fun <DomainType, DataType> flowDataResource(
    dataAction: suspend () -> DataType,
) = FlowBoundResource<DomainType, DataType>(
    dataAction = dataAction,
)
