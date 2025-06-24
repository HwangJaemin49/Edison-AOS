package com.umc.edison.data.datasources

interface PrefDataSource {
    suspend fun <T> get(key: String, default: T): T
    suspend fun <T> set(key: String, value: T)
    suspend fun remove(key: String)
}