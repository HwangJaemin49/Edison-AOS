package com.umc.edison.local.model

import java.util.Date

interface BaseLocal {
    val id: Int
    var createdAt: Date
    var updatedAt: Date
}