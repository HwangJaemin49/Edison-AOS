package com.umc.edison.local.model

interface LocalMapper<DataModel> {
    fun toData(): DataModel
}

internal fun <LocalModel, DataModel> LocalModel.toData(): DataModel {
    @Suppress("UNCHECKED_CAST")
    return when (this) {
        is LocalMapper<*> -> toData()
        is List<*> -> map {
            val localModel: LocalModel = it.toData()
            localModel
        }

        is Unit -> this
        is Boolean -> this
        is Int -> this
        is String -> this
        is Byte -> this
        is Short -> this
        is Long -> this
        is Char -> this
        else -> {
            throw IllegalArgumentException("DataModel은 DataMapper<>, List<DataMapper<>>, Unit중 하나여야 합니다.")
        }
    } as DataModel
}

internal fun <LocalModel : LocalMapper<DataModel>, DataModel> List<LocalModel>.toData(): List<DataModel> {
    return map(LocalMapper<DataModel>::toData)
}
