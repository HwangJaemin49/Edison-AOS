package com.umc.edison.local.model

interface BaseSyncLocal : BaseLocal {
    var isSynced: Boolean
    var isDeleted: Boolean
    override var createdAt: Long?
    override var updatedAt: Long?
    var deletedAt: Long?
}