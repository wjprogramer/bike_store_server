package com.giant_giraffe.data.production.brand

import org.jetbrains.exposed.dao.IntIdTable

/**
 * `name`: 取名為 `brands` 時，`SchemaUtils.create` 會失敗
 */
object BrandTable: IntIdTable("brand") {
    val name = varchar("name", 255)
}