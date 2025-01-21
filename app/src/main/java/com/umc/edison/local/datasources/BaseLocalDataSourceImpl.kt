package com.umc.edison.local.datasources

import androidx.sqlite.db.SimpleSQLiteQuery
import com.umc.edison.local.model.BaseLocal
import com.umc.edison.local.room.dao.BaseDao

open class BaseLocalDataSourceImpl<T : BaseLocal>(
    private val baseDao: BaseDao<T>
) {
    suspend fun insert(entity: T) {
        entity.createdAt = System.currentTimeMillis()
        entity.updatedAt = System.currentTimeMillis()
        baseDao.insert(entity)
    }

    suspend fun update(entity: T) {
        entity.updatedAt = System.currentTimeMillis()
        baseDao.update(entity)
    }

    suspend fun softDelete(entity: T) {
        entity.deletedAt = System.currentTimeMillis()
        entity.isDeleted = true
        baseDao.update(entity)
    }

    suspend fun getUnsyncedDatas(tableName: String): List<T> {
        val query = SimpleSQLiteQuery("SELECT * FROM $tableName WHERE isSynced = 0")
        return baseDao.getUnsyncedDatas(query)
    }

    suspend fun markAsSynced(tableName: String, id: Int) {
        val query = SimpleSQLiteQuery("UPDATE $tableName SET isSynced = 1 WHERE id = $id")
        baseDao.markAsSynced(query)
    }
}