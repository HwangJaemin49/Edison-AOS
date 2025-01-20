package com.umc.edison.local.datasources

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
}