package com.umc.edison.local.datasources

import com.russhwolf.settings.Settings
import com.umc.edison.data.datasources.PrefDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PrefDataSourceImpl @Inject constructor(
    private val settings: Settings
) : PrefDataSource {
    override suspend fun <T> get(key: String, default: T): T = withContext(Dispatchers.Default) {
        when (default) {
            is Boolean -> settings.getBoolean(key, default) as T
            is String -> settings.getString(key, default) as T
            is Int -> settings.getInt(key, default) as T
            is Long -> settings.getLong(key, default) as T
            is Float -> settings.getFloat(key, default) as T
            else -> throw IllegalArgumentException("Unsupported type")
        }
    }

    override suspend fun <T> set(key: String, value: T) {
        withContext(Dispatchers.Default) {
            when (value) {
                is Boolean -> settings.putBoolean(key, value)
                is String -> settings.putString(key, value)
                is Int -> settings.putInt(key, value)
                is Long -> settings.putLong(key, value)
                is Float -> settings.putFloat(key, value)
                else -> throw IllegalArgumentException("Unsupported type: ${value!!::class}")
            }
        }
    }

    override suspend fun remove(key: String) {
        settings.remove(key)
    }
}