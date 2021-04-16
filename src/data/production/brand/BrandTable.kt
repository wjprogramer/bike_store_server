package com.giant_giraffe.data.production.brand

import org.jetbrains.exposed.dao.IntIdTable

/**
 * IntIdTable.`name`: 取名為 `brands` 時，`SchemaUtils.create` 會失敗
 */
object BrandTable: IntIdTable("brand") {
    val name = varchar("name", 255)
    val imageUrl = text("image_url").nullable()
    val isDeleted = bool("is_deleted").default(false)
}