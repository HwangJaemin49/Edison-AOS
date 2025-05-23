package com.umc.edison.local.room.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery

interface BaseSyncDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T)

    @androidx.room.RawQuery
    suspend fun getById(query: SupportSQLiteQuery): T?

    @androidx.room.RawQuery
    suspend fun getAllUnSyncedRows(query: SupportSQLiteQuery): List<T>

    @androidx.room.RawQuery
    suspend fun markAsSynced(query: SupportSQLiteQuery): Int
}