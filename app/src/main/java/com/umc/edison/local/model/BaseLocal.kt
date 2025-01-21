package com.umc.edison.local.model

interface BaseLocal {
    var isSynced: Boolean
    var isDeleted: Boolean
    var createdAt: Long?
    var updatedAt: Long?
    var deletedAt: Long?
}