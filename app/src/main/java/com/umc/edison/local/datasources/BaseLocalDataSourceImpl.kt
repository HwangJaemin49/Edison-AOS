package com.umc.edison.local.datasources

import androidx.sqlite.db.SimpleSQLiteQuery
import com.umc.edison.local.model.BaseSyncLocal
import com.umc.edison.local.room.dao.BaseDao
import java.util.Date

open class BaseLocalDataSourceImpl<T : BaseSyncLocal>(
    private val baseDao: BaseDao<T>
) {
    suspend fun insert(entity: T) {
        entity.createdAt = Date()
        entity.updatedAt = Date()
        entity.isSynced = false
        baseDao.insert(entity)
    }

    suspend fun update(entity: T, tableName: String) {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = ${entity.id}")
        baseDao.getById(query)?.let {
            entity.createdAt = it.createdAt
        }

        entity.updatedAt = Date()
        entity.isSynced = false
        baseDao.update(entity)
    }

    suspend fun softDelete(entity: T, tableName: String) {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE id = ${entity.id}")
        baseDao.getById(query)?.let {
            entity.createdAt = it.createdAt
            entity.updatedAt = it.updatedAt
        }

        entity.deletedAt = Date()
        entity.isDeleted = true
        entity.isSynced = false
        baseDao.update(entity)
    }

    suspend fun getUnSyncedDatas(tableName: String): List<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE is_synced = 0")
        return baseDao.getUnSyncedDatas(query)
    }

    suspend fun markAsSynced(tableName: String, id: Int) {
        val query = SimpleSQLiteQuery("UPDATE $tableName SET is_synced = 1 WHERE id = $id")
        baseDao.markAsSynced(query)
    }

    suspend fun recover(entity: T, tableName: String): Int {
        val query =
            SimpleSQLiteQuery("UPDATE $tableName SET is_deleted = 0 WHERE id = ${entity.id}")
       return baseDao.recover(query)
    }
}