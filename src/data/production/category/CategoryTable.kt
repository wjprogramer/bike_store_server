package com.giant_giraffe.data.production.category

import org.jetbrains.exposed.dao.IntIdTable

object CategoryTable: IntIdTable("categories") {
    val name = varchar("name", 255)
    val imageUrl = text("image_url").nullable()
    val isDeleted = bool("is_deleted").default(false)
}