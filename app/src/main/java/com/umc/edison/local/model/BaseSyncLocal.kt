package com.umc.edison.local.model

import java.util.Date

interface BaseSyncLocal {
    val uuid: String
    var isSynced: Boolean
    var isDeleted: Boolean
    var createdAt: Date
    var updatedAt: Date
    var deletedAt: Date?
}