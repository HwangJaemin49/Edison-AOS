package com.umc.edison.local.model

import java.util.Date

interface BaseSyncLocal : BaseLocal {
    var isSynced: Boolean
    var isDeleted: Boolean
    override var createdAt: Date
    override var updatedAt: Date
    var deletedAt: Date?
}